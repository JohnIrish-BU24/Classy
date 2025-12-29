/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package classy;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Classy {

    public static void main(String[] args) {
        // You can leave this empty or launch your login here
    }

    // ==========================================
    //           BACKEND LOGIC
    // ==========================================

    // --- 1. ADD TO CART (Only checks price, DOES NOT lower stock) ---
    public static void addToCart(java.awt.Component parent, String productName, String size, int qty) {
        // A. Validations
        if (qty <= 0) {
            JOptionPane.showMessageDialog(parent, "Invalid quantity.");
            return;
        }
        if (size.equals("Sizes") || size.equals("Select") || size.trim().isEmpty()) { 
             JOptionPane.showMessageDialog(parent, "Please select a valid Size!");
             return;
        }

        try {
            // B. Find Product in Inventory (Get Price & Check if it exists)
            File inventoryFile = new File("inventory.txt"); 
            if (!inventoryFile.exists()) {
                JOptionPane.showMessageDialog(parent, "Inventory file missing! (inventory.txt)");
                return;
            }

            Scanner scanner = new Scanner(inventoryFile);
            boolean productFound = false;
            double foundPrice = 0.0; 
            int currentStock = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                // Parsing Logic for "Cat Shirt 399 50"
                String[] parts = line.split("\\s+");
                int len = parts.length;
                if (len < 3) continue; 

                String stockStr = parts[len-1]; 
                String priceStr = parts[len-2]; 
                
                String currentName = "";
                for(int i=0; i < len-2; i++) {
                    currentName += parts[i] + " ";
                }
                currentName = currentName.trim();

                if (currentName.equalsIgnoreCase(productName)) {
                    productFound = true;
                    foundPrice = Double.parseDouble(priceStr);
                    currentStock = Integer.parseInt(stockStr);
                    break; 
                }
            }
            scanner.close();

            if (!productFound) {
                JOptionPane.showMessageDialog(parent, "Product not found in inventory!");
                return;
            }

            // C. Warning if stock is too low (But we don't deduct yet)
            if (currentStock < qty) {
                 JOptionPane.showMessageDialog(parent, "Warning: Only " + currentStock + " left in stock. Proceeding anyway.");
            }

            // D. Write to Cart.txt
            FileWriter fwCart = new FileWriter("Cart.txt", true);
            double total = foundPrice * qty;
            // Format: Name, Size, Qty, Price, Total
            fwCart.write(productName + "," + size + "," + qty + "," + foundPrice + "," + total + "\n");
            fwCart.close();

            JOptionPane.showMessageDialog(parent, "Added to Cart!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
        }
    }

    // --- 2. LOAD CART TABLE ---
    public static void loadCartTable(javax.swing.JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        
        File file = new File("Cart.txt");
        if (!file.exists()) return;

        try {
            Scanner scanner = new Scanner(file);
            int id = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    model.addRow(new Object[]{
                        id++, 
                        parts[0], 
                        parts[1], 
                        Integer.parseInt(parts[2]), 
                        Double.parseDouble(parts[3]), 
                        Double.parseDouble(parts[4])
                    });
                }
            }
            scanner.close();
        } catch (Exception e) {}
    }

    // --- 3. EMPTY CART ---
    public static void emptyCart(java.awt.Component parent, javax.swing.JTable table) {
        try {
            new FileWriter("Cart.txt", false).close();
            ((DefaultTableModel) table.getModel()).setRowCount(0);
            JOptionPane.showMessageDialog(parent, "Cart Emptied.");
        } catch (IOException e) {}
    }

    // --- 4. CHECK OUT (Updates Inventory Here!) ---
    public static void checkOut(java.awt.Component parent, javax.swing.JTable table) {
        try {
            File cartFile = new File("Cart.txt");
            File inventoryFile = new File("inventory.txt");

            if (!cartFile.exists() || cartFile.length() == 0) {
                JOptionPane.showMessageDialog(parent, "Cart is empty!");
                return;
            }

            // 1. Read Inventory into Memory
            ArrayList<String> inventoryLines = new ArrayList<>();
            Scanner invScanner = new Scanner(inventoryFile);
            while (invScanner.hasNextLine()) inventoryLines.add(invScanner.nextLine());
            invScanner.close();

            // 2. Process Cart and Deduct Stock
            Scanner cartScanner = new Scanner(cartFile);
            ArrayList<String> receiptItems = new ArrayList<>();
            double grandTotal = 0;
            boolean stockIssue = false;

            while (cartScanner.hasNextLine()) {
                String cartLine = cartScanner.nextLine();
                if (cartLine.trim().isEmpty()) continue;
                
                String[] cartParts = cartLine.split(",");
                String cartName = cartParts[0];
                int cartQty = Integer.parseInt(cartParts[2]);
                double itemTotal = Double.parseDouble(cartParts[4]);

                // Find and Update in Inventory Memory
                for (int i = 0; i < inventoryLines.size(); i++) {
                    String invLine = inventoryLines.get(i);
                    if (invLine.trim().isEmpty()) continue;

                    String[] invParts = invLine.split("\\s+");
                    int len = invParts.length;
                    if (len < 3) continue;

                    String stockStr = invParts[len-1];
                    String priceStr = invParts[len-2];
                    
                    String invName = "";
                    for(int k=0; k < len-2; k++) invName += invParts[k] + " ";
                    invName = invName.trim();

                    if (invName.equalsIgnoreCase(cartName)) {
                        int currentStock = Integer.parseInt(stockStr);
                        if (currentStock >= cartQty) {
                            int newStock = currentStock - cartQty;
                            inventoryLines.set(i, invName + " " + priceStr + " " + newStock);
                            
                            receiptItems.add(cartName + " (" + cartParts[1] + ") x" + cartQty + " = $" + itemTotal);
                            grandTotal += itemTotal;
                        } else {
                            JOptionPane.showMessageDialog(parent, "Error: Not enough stock for " + cartName);
                            stockIssue = true;
                        }
                        break;
                    }
                }
                if (stockIssue) break;
            }
            cartScanner.close();

            if (stockIssue) return; 

            // 3. Save Changes to Inventory File
            FileWriter fwInv = new FileWriter("inventory.txt", false);
            for (String line : inventoryLines) fwInv.write(line + "\n");
            fwInv.close();

            // 4. Print Receipt
            FileWriter fwRec = new FileWriter("Receipt.txt");
            fwRec.write("------ OFFICIAL RECEIPT ------\n\n");
            for(String item : receiptItems) fwRec.write(item + "\n");
            fwRec.write("\n----------------------------\n");
            fwRec.write("GRAND TOTAL: $" + grandTotal);
            fwRec.close();

            JOptionPane.showMessageDialog(parent, "Checkout Complete!");
            emptyCart(parent, table);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Checkout Error: " + e.getMessage());
        }
    }
}
