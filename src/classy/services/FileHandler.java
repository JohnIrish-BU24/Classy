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

    // LOAD ALL ORDERS (For the Orders Table)
    public static List<Order> loadAllOrders() {
        List<Order> orders = new ArrayList<>();
        File file = new File(RECEIPT_FILE);
        if (!file.exists()) return orders;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    // Create Order object: Date, Time, Tracker, Total
                    orders.add(new Order(parts[0], parts[1], parts[2], Double.parseDouble(parts[3])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    // LOAD ITEMS FOR A SPECIFIC ORDER (For the Receipt Popup)
    public static List<OrderItem> loadItemsByTracker(String trackerToFind) {
        List<OrderItem> items = new ArrayList<>();
        File file = new File(DETAILS_FILE);
        if (!file.exists()) return items;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;

                String[] d = line.split(",");
                // Check if this line belongs to the tracker we are looking for
                if (d[0].equals(trackerToFind)) {
                    // d[1]=Name, d[2]=Size, d[3]=Qty, d[4]=Price, d[5]=Total
                    items.add(new OrderItem(d[1], d[2], Integer.parseInt(d[3]), 
                             Double.parseDouble(d[4]), Double.parseDouble(d[5])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}
