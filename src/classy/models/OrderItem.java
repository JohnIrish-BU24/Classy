/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.models;

public class OrderItem {
    private String productName;
    private String size;
    private int quantity;
    private double price;
    private double itemTotal;

    public OrderItem(String name, String size, int qty, double price, double total) {
        this.productName = name;
        this.size = size;
        this.quantity = qty;
        this.price = price;
        this.itemTotal = total;
    }

    public String getName() { return productName; }
    public String getSize() { return size; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotal() { return itemTotal; }
}