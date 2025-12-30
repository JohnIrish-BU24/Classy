/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author milal
 */
public class Product {
    
    // Initialize variables for the product objects
    protected String productName;
    protected int quantity;
    protected double price;
    protected String size;
    
    public Product(String name, int quantity, double price, String size) {
        this.productName = name;
        this.quantity = quantity;
        this.price = price;
        this.size = size;
    }

    // Getters
    public String getName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getSize() { return size; }
    
    
    
    public static void addToCart(java.awt.Component parent, String productName, String size, int qty, String category) {
    // 1. Keep your existing Validations
    if (qty <= 0) {
        JOptionPane.showMessageDialog(parent, "Invalid quantity.");
        return;
    }
    if (size.equals("Sizes") || size.equals("Select") || size.trim().isEmpty()) { 
         JOptionPane.showMessageDialog(parent, "Please select a valid Size!");
         return;
    }

    try {
        // 2. Keep your Inventory Search Logic
        File inventoryFile = new File("Inventory.txt"); 
        Scanner scanner = new Scanner(inventoryFile);
        boolean productFound = false;
        double foundPrice = 0.0; 
        int currentStock = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split("\\s+");
            int len = parts.length;
            if (len < 3) continue; 

            String currentName = "";
            for(int i=0; i < len-2; i++) currentName += parts[i] + " ";
            currentName = currentName.trim();

            if (currentName.equalsIgnoreCase(productName)) {
                productFound = true;
                foundPrice = Double.parseDouble(parts[len-2]);
                currentStock = Integer.parseInt(parts[len-1]);
                break; 
            }
        }
        scanner.close();

        if (!productFound) {
            JOptionPane.showMessageDialog(parent, "Product not found!");
            return;
        }

        // 3. OOP ENCAPSULATION & INHERITANCE
        // Create the specific object based on category
        classy.Product item;
        if (category.equalsIgnoreCase("Shirts")) {
            item = new classy.Shirts(productName, qty, foundPrice, size);
        } else if (category.equalsIgnoreCase("Pants")) {
            item = new classy.Pants(productName, qty, foundPrice, size);
        } else {
            item = new classy.Product(productName, qty, foundPrice, size);
        }

        // 4. Use the Object to write to Cart.txt
        FileWriter fwCart = new FileWriter("Cart.txt", true);
        double total = item.getPrice() * item.getQuantity();
        fwCart.write(item.getName() + "," + item.getSize() + "," + 
                     item.getQuantity() + "," + item.getPrice() + "," + total + "\n");
        fwCart.close();

        JOptionPane.showMessageDialog(parent, "Added to Cart!");

    } catch (Exception e) {
        JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
    }
}
    
}



