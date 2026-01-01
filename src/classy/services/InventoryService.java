/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.services;

import classy.models.Product;
import classy.models.Shirts;
import classy.models.Pants;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class InventoryService {

    private static final String INVENTORY_FILE = "Inventory.txt";
    private static final String CART_FILE = "Cart.txt";

    public static void addToCart(java.awt.Component parent, String name, String size, int qty, String category) {
        if (qty <= 0) {
            JOptionPane.showMessageDialog(parent, "Invalid quantity.");
            return;
        }
        if (size.equals("Sizes") || size.equals("Select") || size.trim().isEmpty()) { 
             JOptionPane.showMessageDialog(parent, "Please select a valid Size!");
             return;
        }

        try {
            File invFile = new File(INVENTORY_FILE);
            if (!invFile.exists()) {
                JOptionPane.showMessageDialog(parent, "Inventory file missing!");
                return;
            }

            // 1. SPACE-RESISTANT PARSING & STOCK CHECK
            Scanner scanner = new Scanner(invFile);
            boolean found = false;
            double price = 0.0;
            int currentStock = 0;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                
                String[] parts = line.split("\\s+"); // Split by whitespace
                if (parts.length < 3) continue;

                // Logic: Price is 2nd to last, Stock is last
                double p = Double.parseDouble(parts[parts.length - 2]);
                int s = Integer.parseInt(parts[parts.length - 1]);
                
                // Reconstruct Name (Everything before Price)
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < parts.length - 2; i++) {
                    nameBuilder.append(parts[i]).append(" ");
                }
                String currentName = nameBuilder.toString().trim();

                if (currentName.equalsIgnoreCase(name)) {
                    found = true;
                    price = p;
                    currentStock = s;
                    break;
                }
            }
            scanner.close();

            if (!found) {
                JOptionPane.showMessageDialog(parent, "Product not found!");
                return;
            }
            
            // 2. NEGATIVE STOCK CHECK
            if (currentStock < qty) {
                JOptionPane.showMessageDialog(parent, "Not enough stock! Available: " + currentStock);
                return;
            }

            // 3. IMMEDIATE DEDUCTION (Update Inventory File)
            updateInventoryStock(name, size, qty);

            // 4. WRITE TO CART (Polymorphism)
            Product item;
            if (category.equalsIgnoreCase("Shirts")) item = new Shirts(name, qty, price, size);
            else if (category.equalsIgnoreCase("Pants")) item = new Pants(name, qty, price, size);
            else item = new Product(name, qty, price, size);

            FileWriter fw = new FileWriter(CART_FILE, true);
            fw.write(item.getName() + "," + item.getSize() + "," + item.getQuantity() + "," + 
                     item.getPrice() + "," + item.calculateSubtotal() + "\n");
            fw.close();

            JOptionPane.showMessageDialog(parent, "Added to Cart!");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
        }
    }

    private static void updateInventoryStock(String productName, String size, int qtyToDeduct) {
        List<String> lines = new ArrayList<>();
        File file = new File("Inventory.txt");
        
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) { lines.add(line); continue; }
                
                String[] parts = line.split("\\s+");
                int len = parts.length;
                if (len < 6) { lines.add(line); continue; }

                // Parse Name
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < len - 5; i++) {
                    nameBuilder.append(parts[i]).append(" ");
                }
                String currentName = nameBuilder.toString().trim();

                // CHECK MATCH (Ignore " Small" suffix if passed from button)
                String searchName = productName.replace(" Small", "").replace(" Medium", "").replace(" Large", "");
                
                if (currentName.equalsIgnoreCase(searchName)) {
                    // Parse existing stocks
                    double price = Double.parseDouble(parts[len - 5]);
                    int total = Integer.parseInt(parts[len - 4]);
                    int s = Integer.parseInt(parts[len - 3]);
                    int m = Integer.parseInt(parts[len - 2]);
                    int l = Integer.parseInt(parts[len - 1]);

                    // DEDUCT LOGIC
                    boolean enoughStock = true;
                    if (size.equalsIgnoreCase("Small")) {
                        if(s >= qtyToDeduct) s -= qtyToDeduct; else enoughStock = false;
                    } else if (size.equalsIgnoreCase("Medium")) {
                        if(m >= qtyToDeduct) m -= qtyToDeduct; else enoughStock = false;
                    } else if (size.equalsIgnoreCase("Large")) {
                        if(l >= qtyToDeduct) l -= qtyToDeduct; else enoughStock = false;
                    }
                    
                    if (enoughStock) {
                        total -= qtyToDeduct; // Always deduct from total
                    }

                    // Reconstruct Line: Name Price Total S M L
                    lines.add(currentName + " " + price + " " + total + " " + s + " " + m + " " + l);
                } else {
                    lines.add(line);
                }
            }
            
            // Rewrite File
            FileWriter fw = new FileWriter(file, false);
            for (String l : lines) fw.write(l + "\n");
            fw.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}