/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.services;

import classy.models.Product;
import classy.models.Shirts;
import classy.models.Pants;
import classy.models.Dresses; // Ensure you have this model
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class InventoryService {

    private static final String INVENTORY_FILE = "Inventory.txt";
    private static final String CART_FILE = "Cart.txt";

    public static void addToCart(java.awt.Component parent, String productName, String size, int qty, String category) {
        // 1. Validation Logic
        if (qty <= 0) {
            JOptionPane.showMessageDialog(parent, "Invalid quantity.");
            return;
        }
        if (size.equals("Sizes") || size.equals("Select") || size.trim().isEmpty()) { 
             JOptionPane.showMessageDialog(parent, "Please select a valid Size!");
             return;
        }

        try {
            // 2. Inventory Search Logic
            File inventoryFile = new File(INVENTORY_FILE); 
            if (!inventoryFile.exists()) {
                JOptionPane.showMessageDialog(parent, "Inventory file not found!");
                return;
            }

            Scanner scanner = new Scanner(inventoryFile);
            boolean productFound = false;
            double foundPrice = 0.0; 
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\s+"); // Split by whitespace
                int len = parts.length;
                if (len < 3) continue; 

                // Reconstruct name logic (in case name has spaces)
                String currentName = "";
                for(int i=0; i < len-2; i++) currentName += parts[i] + " ";
                currentName = currentName.trim();

                if (currentName.equalsIgnoreCase(productName)) {
                    productFound = true;
                    foundPrice = Double.parseDouble(parts[len-2]);
                    break; 
                }
            }
            scanner.close();

            if (!productFound) {
                JOptionPane.showMessageDialog(parent, "Product not found in Inventory!");
                return;
            }

            // 3. Create the Model Object (Polymorphism)
            Product item;
            if (category.equalsIgnoreCase("Shirts")) {
                item = new Shirts(productName, qty, foundPrice, size);
            } else if (category.equalsIgnoreCase("Pants")) {
                item = new Pants(productName, qty, foundPrice, size);
            } else if (category.equalsIgnoreCase("Dresses")) {
                item = new Dresses(productName, qty, foundPrice, size);
            } else {
                item = new Product(productName, qty, foundPrice, size);
            }

            // 4. Write to Cart File
            FileWriter fwCart = new FileWriter(CART_FILE, true);
            double total = item.calculateSubtotal(); // Using the Model's method
            
            // Format: Name,Size,Qty,Price,Total
            fwCart.write(item.getName() + "," + item.getSize() + "," + 
                         item.getQuantity() + "," + item.getPrice() + "," + total + "\n");
            fwCart.close();

            JOptionPane.showMessageDialog(parent, "Added to Cart successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error processing cart: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
