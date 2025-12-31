/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.services;

import classy.models.Order;
import classy.models.OrderItem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    private static final String RECEIPT_FILE = "Receipt.txt";
    private static final String DETAILS_FILE = "OrderDetails.txt";

    public static List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(RECEIPT_FILE))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p.length >= 4) {
                    orders.add(new Order(p[0], p[1], p[2], Double.parseDouble(p[3])));
                }
            }
        } catch (Exception e) { }
        return orders;
    }

    public static List<OrderItem> loadItemsByTracker(String tracker) {
        List<OrderItem> items = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(DETAILS_FILE))) {
            while (sc.hasNextLine()) {
                String[] p = sc.nextLine().split(",");
                if (p[0].equals(tracker)) {
                    items.add(new OrderItem(p[1], p[2], Integer.parseInt(p[3]), 
                              Double.parseDouble(p[4]), Double.parseDouble(p[5])));
                }
            }
        } catch (Exception e) { }
        return items;
    }
    
    public static void clearHistory() {
    try {
        new java.io.FileWriter("Receipt.txt", false).close();
        new java.io.FileWriter("OrderDetails.txt", false).close();
    } catch (Exception e) { e.printStackTrace(); }
}
}