/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.services;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CheckoutService {
    private static final String CART_FILE = "Cart.txt";
    private static final String DETAILS_FILE = "OrderDetails.txt";
    private static final String RECEIPT_FILE = "Receipt.txt";

    public static String processCheckout() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String tracker = "CLS-" + (int)(Math.random()*9000 + 1000) + (char)((int)(Math.random()*26)+65);
        
        StringBuilder receiptLog = new StringBuilder();
        double grandTotal = 0;
        List<String> cartData = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(CART_FILE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (!line.trim().isEmpty()) cartData.add(line);
            }
        } catch (Exception e) { return "Error reading cart"; }

        if (cartData.isEmpty()) return "Cart is empty!";

        try (FileWriter fwDetails = new FileWriter(DETAILS_FILE, true)) {
            for (String line : cartData) {
                String[] p = line.split(",");
                if(p.length < 5) continue;
                String name = p[0];
                int qty = Integer.parseInt(p[2]);
                double itemTotal = Double.parseDouble(p[4]);
                grandTotal += itemTotal;

                fwDetails.write(tracker + "," + line + "\n");
                receiptLog.append(String.format("%-15s x%-3d ₱%-10.2f\n", name, qty, itemTotal));
            }
        } catch (Exception e) { return "Error saving details"; }

        try (FileWriter fwReceipt = new FileWriter(RECEIPT_FILE, true)) {
            fwReceipt.write(date + "," + time + "," + tracker + "," + grandTotal + "\n");
        } catch (Exception e) { return "Error saving receipt"; }

        StringBuilder receipt = new StringBuilder();
        receipt.append("-------- CLASSy Receipt --------\n");
        receipt.append("Date: ").append(date).append(" Time: ").append(time).append("\n");
        receipt.append("Order: ").append(tracker).append("\n");
        receipt.append("--------------------------------\n");
        receipt.append(String.format("%-15s %-5s %-10s\n", "Item", "Qty", "Total (₱)"));
        receipt.append(receiptLog);
        receipt.append("--------------------------------\n");
        receipt.append(String.format("Grand Total: ₱%.2f\n", grandTotal));

        return receipt.toString();
    }
}