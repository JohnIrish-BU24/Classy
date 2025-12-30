package classy;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * @author milal
 */
public class Product {
    
    // Encapsulated variables
    protected String productName;
    protected double price;
    // Stock levels from the 6-column file
    protected int totalStock;
    protected int smallStock;
    protected int medStock;
    protected int largeStock;
    protected String size; // The size chosen for the cart
    
    // Constructor (6 parameters to match Inventory.txt structure)
    public Product(String name, double price, int total, int s, int m, int l) {
        this.productName = name;
        this.price = price;
        this.totalStock = total;
        this.smallStock = s;
        this.medStock = m;
        this.largeStock = l;
    }

    // Getters
    public String getName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return totalStock; }
    public String getSize() { return size; }
    
    // Setter for the selected size
    public void setSize(String size) {
        this.size = size;
    }
    
    // Helper to get stock based on a string selection
    public int getStockBySize(String sizeRequested) {
        return switch (sizeRequested) {
            case "Small" -> smallStock;
            case "Medium" -> medStock;
            case "Large" -> largeStock;
            default -> totalStock; 
        };
    }

    // --- ADD TO CART LOGIC ---
    public static void addToCart(java.awt.Component parent, String productName, String size, int qty, String category) {
        if (qty <= 0) {
            JOptionPane.showMessageDialog(parent, "Invalid quantity.");
            return;
        }
        if (size.equals("Sizes") || size.equals("Select") || size.trim().isEmpty()) { 
             JOptionPane.showMessageDialog(parent, "Please select a valid Size!");
             return;
        }

        try {
            File inventoryFile = new File("Inventory.txt"); 
            Scanner scanner = new Scanner(inventoryFile);
            boolean productFound = false;
            
            double foundPrice = 0.0; 
            int t = 0, s = 0, m = 0, l = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\s+");
                int len = parts.length;
                if (len < 6) continue; 

                // Reconstruct Name (handles spaces)
                String currentName = "";
                for(int i=0; i < len-5; i++) currentName += parts[i] + " ";
                currentName = currentName.trim();

                if (currentName.equalsIgnoreCase(productName)) {
                    productFound = true;
                    foundPrice = Double.parseDouble(parts[len-5]);
                    t = Integer.parseInt(parts[len-4]);
                    s = Integer.parseInt(parts[len-3]);
                    m = Integer.parseInt(parts[len-2]);
                    l = Integer.parseInt(parts[len-1]);
                    break; 
                }
            }
            scanner.close();

            if (!productFound) {
                JOptionPane.showMessageDialog(parent, "Product not found!");
                return;
            }

            Product item = new Product(productName, foundPrice, t, s, m, l);
            
            if (qty > item.getStockBySize(size)) {
                JOptionPane.showMessageDialog(parent, "Insufficient stock! Only " + item.getStockBySize(size) + " left.");
                return;
            }

            // Write to Cart.txt
            FileWriter fwCart = new FileWriter("Cart.txt", true);
            double itemTotal = item.getPrice() * qty;
            fwCart.write(item.getName() + "," + size + "," + qty + "," + item.getPrice() + "," + itemTotal + "\n");
            fwCart.close();

            JOptionPane.showMessageDialog(parent, productName + " added to cart!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Error: " + e.getMessage());
        }
    }

    // --- NEW: UPDATE INVENTORY LOGIC (For Checkout) ---
    public static void updateInventoryStock(String name, String size, int qtyBought) {
        File file = new File("Inventory.txt");
        StringBuilder newContent = new StringBuilder();

        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                if (parts.length < 6) {
                    newContent.append(line).append("\n");
                    continue;
                }

                // Reconstruct product name
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < parts.length - 5; i++) {
                    nameBuilder.append(parts[i]).append(i == parts.length - 6 ? "" : " ");
                }
                String currentName = nameBuilder.toString();

                if (currentName.equalsIgnoreCase(name)) {
                    double price = Double.parseDouble(parts[parts.length - 5]);
                    int total = Integer.parseInt(parts[parts.length - 4]);
                    int s = Integer.parseInt(parts[parts.length - 3]);
                    int m = Integer.parseInt(parts[parts.length - 2]);
                    int l = Integer.parseInt(parts[parts.length - 1]);

                    // Subtract the bought quantity from the specific size
                    switch (size) {
                        case "Small" -> s -= qtyBought;
                        case "Medium" -> m -= qtyBought;
                        case "Large" -> l -= qtyBought;
                    }
                    total -= qtyBought; // Always subtract from total too

                    // Add the updated line back to content
                    newContent.append(currentName).append(" ").append((int)price).append(" ")
                              .append(total).append(" ").append(s).append(" ").append(m)
                              .append(" ").append(l).append("\n");
                } else {
                    newContent.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Error reducing stock: " + e.getMessage());
        }

        // Rewrite the entire Inventory file with the updated stocks
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(newContent.toString());
        } catch (Exception e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }
}