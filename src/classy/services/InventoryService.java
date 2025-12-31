/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.services;

import classy.models.*; 
import java.io.File;
import java.io.FileWriter;
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

            Scanner scanner = new Scanner(invFile);
            boolean found = false;
            double price = 0.0;
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length < 3) continue;

                String currentName = "";
                for(int i=0; i < parts.length - 2; i++) currentName += parts[i] + " ";
                
                if (currentName.trim().equalsIgnoreCase(name)) {
                    found = true;
                    price = Double.parseDouble(parts[parts.length - 2]);
                    break;
                }
            }
            scanner.close();

            if (!found) {
                JOptionPane.showMessageDialog(parent, "Product not found!");
                return;
            }

            // Polymorphism
            Product item;
            if (category.equalsIgnoreCase("Shirts")) item = new Shirts(name, qty, price, size);
            else if (category.equalsIgnoreCase("Pants")) item = new Pants(name, qty, price, size);
            else if (category.equalsIgnoreCase("Dresses")) item = new Dresses(name, qty, price, size);
            else item = new Product(name, qty, price, size);

            FileWriter fw = new FileWriter(CART_FILE, true);
            fw.write(item.getName() + "," + item.getSize() + "," + item.getQuantity() + "," + 
                     item.getPrice() + "," + item.calculateSubtotal() + "\n");
            fw.close();

            JOptionPane.showMessageDialog(parent, "Added to Cart!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}