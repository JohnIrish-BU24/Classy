/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy;

/**
 *
 * @author milal
 */
public class Product {
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
}

