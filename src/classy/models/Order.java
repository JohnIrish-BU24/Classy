/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.models;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String date;
    private String time;
    private String tracker;
    private double grandTotal;
    private List<OrderItem> items;

    public Order(String date, String time, String tracker, double grandTotal) {
        this.date = date;
        this.time = time;
        this.tracker = tracker;
        this.grandTotal = grandTotal;
        this.items = new ArrayList<>();
    }

    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getTracker() { return tracker; }
    public double getGrandTotal() { return grandTotal; }
    public List<OrderItem> getItems() { return items; }
    
    public void addItem(OrderItem item) { this.items.add(item); }
}