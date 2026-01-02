/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package classy.views.Product_List;

import classy.services.InventoryService;
import classy.models.Product;

import classy.views.Cart.CartJFrame;
import classy.views.Orders.OrdersJFrame;
import classy.views.LogIn.LogInJFrame;
import java.io.File;
import java.util.Scanner;
import javax.swing.JOptionPane;


public class Product_ListJFrame_Pants extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Product_ListJFrame_Pants.class.getName());
    
    // OOP
    private java.util.HashMap<String, classy.models.Product> productMap = new java.util.HashMap<>();
    
    
    public Product_ListJFrame_Pants() {
        initComponents();
        
        loadProductData();
        
        this.setSize(1250, 670);
        
        this.setResizable(false);
        
        this.setLocationRelativeTo(null);
    }
    
    public void loadProductData() {
        try {
            java.io.File file = new java.io.File("Inventory.txt");
            if (!file.exists()) {
                System.out.println("ERROR: Inventory.txt not found!");
                return;
            }

            java.util.Scanner reader = new java.util.Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\s+");
                int len = parts.length;

                // Safety: We need at least 6 columns
                if (len < 6) continue; 

                // 1. Parse Numbers from the END
                int l = Integer.parseInt(parts[len - 1]);
                int m = Integer.parseInt(parts[len - 2]);
                int s = Integer.parseInt(parts[len - 3]);
                int total = Integer.parseInt(parts[len - 4]);
                double price = Double.parseDouble(parts[len - 5]);

                // 2. Parse Name from the START
                StringBuilder nameBuilder = new StringBuilder();
                for (int i = 0; i < len - 5; i++) {
                    nameBuilder.append(parts[i]).append(" ");
                }
                String name = nameBuilder.toString().trim();

                // 3. Store in Map
                productMap.put(name, new classy.models.Product(name, total, price, "Sizes"));
                productMap.put(name + " Small", new classy.models.Product(name, s, price, "Small"));
                productMap.put(name + " Medium", new classy.models.Product(name, m, price, "Medium"));
                productMap.put(name + " Large", new classy.models.Product(name, l, price, "Large"));
            }
            reader.close();

            // Refresh UI
            displayProductInfo(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayProductInfo() {
        // MAPPING: Ensure labels (jLabel6, jLabel16, etc.) match your design for these items
        updateLabel("Black Pants", jLabel6, jLabel16, jLabel44);
        updateLabel("Star Pants", jLabel28, jLabel31, jLabel40);
        updateLabel("Hiphop", jLabel23, jLabel26, jLabel41);
        updateLabel("White Pants", jLabel11, jLabel17, jLabel39);
        updateLabel("Pink Pants", jLabel3, jLabel15, jLabel36);
        updateLabel("Denim", jLabel18, jLabel21, jLabel42);
    }
    
    private void updateLabel(String baseName, javax.swing.JLabel idLbl, javax.swing.JLabel priceLbl, javax.swing.JLabel stockLbl) {
        // 1. Set Name
        idLbl.setText(baseName);

        // 2. Set Price (Look up base product)
        if (productMap.containsKey(baseName)) {
            double price = productMap.get(baseName).getPrice();
            priceLbl.setText(String.format("₱%.2f", price));
        } else {
            priceLbl.setText("N/A");
        }

        // 3. Set Stock (Dynamic based on Dropdown)
        int currentStock = 0;
        String selectedSize = "Sizes";

        if (jComboBox2 != null && jComboBox2.getSelectedItem() != null) {
            selectedSize = jComboBox2.getSelectedItem().toString();
        }

        if (selectedSize.equals("Sizes") || selectedSize.equals("Select")) {
            // Show Total Stock
            if (productMap.containsKey(baseName)) {
                currentStock = productMap.get(baseName).getQuantity();
            }
        } else {
            // Show Specific Size Stock (e.g., "Cat Shirt Small")
            String specificKey = baseName + " " + selectedSize; 
            if (productMap.containsKey(specificKey)) {
                currentStock = productMap.get(specificKey).getQuantity();
            }
        }

        stockLbl.setText(String.valueOf(currentStock));
    }
    
    // --- REUSABLE HELPER FOR ADD TO CART ---
    private void handleAddToCart(String baseName, javax.swing.JSpinner spinner) {
        int qty = (Integer) spinner.getValue();
        String size = jComboBox2.getSelectedItem().toString(); 

        // 1. Check Size
        if (size.equals("Sizes") || size.equals("Select")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a specific Size.");
            return;
        }

        // 2. Check Quantity
        if (qty <= 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            return;
        }

        // 3. Check Product & Stock
        String specificKey = baseName + " " + size;

        if (productMap.containsKey(specificKey)) {
            // Pass "Shirts" category
            classy.services.InventoryService.addToCart(this, baseName, size, qty, "Pants");

            // Reset & Refresh
            spinner.setValue(0);
            loadProductData(); 
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Item Unavailable.");
        }
    }
    
    private void updateMeasurementLabel() {
        String selectedSize = jComboBox2.getSelectedItem().toString();

        if (selectedSize.equals("Sizes") || selectedSize.equals("Select")) {
            // Hide if no size selected (Make sure lblMeasurement exists in Design!)
            if(lblMeasurement != null) lblMeasurement.setText(""); 
        } else {
            // USE SHIRTS MODEL
            classy.models.Pants temp = new classy.models.Pants("Temp", 0, 0, selectedSize);
            if(lblMeasurement != null) lblMeasurement.setText(temp.getMeasurementDescription());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();
        jButton8 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSpinner2 = new javax.swing.JSpinner();
        jButton9 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jSpinner3 = new javax.swing.JSpinner();
        jButton10 = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSpinner4 = new javax.swing.JSpinner();
        jButton11 = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSpinner5 = new javax.swing.JSpinner();
        jButton12 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jSpinner6 = new javax.swing.JSpinner();
        jButton13 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        lblMeasurement = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1000, 600));

        jPanel1.setBackground(new java.awt.Color(242, 227, 202));

        jButton1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton1.setText("Cart");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setBackground(new java.awt.Color(102, 51, 0));
        jButton2.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Product List");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton3.setText("Orders");
        jButton3.addActionListener(this::jButton3ActionPerformed);

        jButton4.setBackground(new java.awt.Color(242, 227, 202));
        jButton4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton4.setText("Log Out");
        jButton4.addActionListener(this::jButton4ActionPerformed);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Orders/Logo2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel2.setBackground(new java.awt.Color(177, 108, 25));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Product List");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(177, 108, 25));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(null);

        jButton6.setBackground(new java.awt.Color(177, 108, 25));
        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Pants");
        jButton6.addActionListener(this::jButton6ActionPerformed);
        jPanel5.add(jButton6);
        jButton6.setBounds(145, 16, 107, 32);

        jButton7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton7.setText("Dresses");
        jButton7.addActionListener(this::jButton7ActionPerformed);
        jPanel5.add(jButton7);
        jButton7.setBounds(264, 16, 107, 32);

        jPanel6.setBackground(new java.awt.Color(242, 227, 202));
        jPanel6.setLayout(null);

        jLabel3.setText("Product ID");
        jPanel6.add(jLabel3);
        jLabel3.setBounds(90, 100, 70, 17);

        jLabel4.setText("Price:");
        jPanel6.add(jLabel4);
        jLabel4.setBounds(15, 118, 60, 17);

        jLabel5.setText("Quantity:");
        jPanel6.add(jLabel5);
        jLabel5.setBounds(15, 143, 90, 17);

        jLabel15.setText("$$$");
        jPanel6.add(jLabel15);
        jLabel15.setBounds(49, 118, 60, 17);

        jSpinner1.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel6.add(jSpinner1);
        jSpinner1.setBounds(68, 140, 64, 23);

        jButton8.setBackground(new java.awt.Color(102, 51, 0));
        jButton8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Add to Cart");
        jButton8.addActionListener(this::jButton8ActionPerformed);
        jPanel6.add(jButton8);
        jButton8.setBounds(10, 180, 222, 34);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/12.png"))); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel6.add(jLabel7);
        jLabel7.setBounds(70, 0, 100, 100);

        jLabel43.setText("Stock:");
        jPanel6.add(jLabel43);
        jLabel43.setBounds(160, 120, 43, 17);

        jLabel36.setText("#");
        jPanel6.add(jLabel36);
        jLabel36.setBounds(200, 120, 40, 17);

        jPanel5.add(jPanel6);
        jPanel6.setBounds(370, 320, 240, 220);

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton5.setText("Shirts");
        jButton5.addActionListener(this::jButton5ActionPerformed);
        jPanel5.add(jButton5);
        jButton5.setBounds(20, 16, 107, 32);

        jPanel7.setBackground(new java.awt.Color(242, 227, 202));
        jPanel7.setLayout(null);

        jLabel6.setText("Product ID");
        jPanel7.add(jLabel6);
        jLabel6.setBounds(90, 100, 80, 17);

        jLabel8.setText("Price:");
        jPanel7.add(jLabel8);
        jLabel8.setBounds(15, 118, 50, 17);

        jLabel9.setText("Quantity:");
        jPanel7.add(jLabel9);
        jLabel9.setBounds(15, 143, 120, 17);

        jLabel16.setText("$$$");
        jPanel7.add(jLabel16);
        jLabel16.setBounds(49, 118, 60, 17);

        jSpinner2.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel7.add(jSpinner2);
        jSpinner2.setBounds(68, 140, 64, 23);

        jButton9.setBackground(new java.awt.Color(102, 51, 0));
        jButton9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Add to Cart");
        jButton9.addActionListener(this::jButton9ActionPerformed);
        jPanel7.add(jButton9);
        jButton9.setBounds(10, 180, 222, 34);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/7.png"))); // NOI18N
        jLabel10.setText("jLabel7");
        jPanel7.add(jLabel10);
        jLabel10.setBounds(70, 10, 100, 90);

        jLabel38.setText("Stock:");
        jPanel7.add(jLabel38);
        jLabel38.setBounds(150, 120, 40, 17);

        jLabel44.setText("#");
        jPanel7.add(jLabel44);
        jLabel44.setBounds(200, 120, 43, 17);

        jPanel5.add(jPanel7);
        jPanel7.setBounds(60, 80, 240, 220);

        jPanel8.setBackground(new java.awt.Color(242, 227, 202));
        jPanel8.setLayout(null);

        jLabel11.setText("Product ID");
        jPanel8.add(jLabel11);
        jLabel11.setBounds(90, 100, 80, 17);

        jLabel12.setText("Price:");
        jPanel8.add(jLabel12);
        jLabel12.setBounds(15, 118, 40, 17);

        jLabel13.setText("Quantity:");
        jPanel8.add(jLabel13);
        jLabel13.setBounds(15, 143, 120, 17);

        jLabel17.setText("$$$");
        jPanel8.add(jLabel17);
        jLabel17.setBounds(49, 118, 60, 17);

        jSpinner3.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel8.add(jSpinner3);
        jSpinner3.setBounds(68, 140, 64, 23);

        jButton10.setBackground(new java.awt.Color(102, 51, 0));
        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Add to Cart");
        jButton10.addActionListener(this::jButton10ActionPerformed);
        jPanel8.add(jButton10);
        jButton10.setBounds(10, 180, 222, 34);

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/11.png"))); // NOI18N
        jLabel14.setText("jLabel7");
        jPanel8.add(jLabel14);
        jLabel14.setBounds(70, 0, 100, 100);

        jLabel33.setText("Stock:");
        jPanel8.add(jLabel33);
        jLabel33.setBounds(150, 120, 50, 17);

        jLabel39.setText("#");
        jPanel8.add(jLabel39);
        jLabel39.setBounds(200, 120, 43, 17);

        jPanel5.add(jPanel8);
        jPanel8.setBounds(60, 320, 240, 220);

        jPanel9.setBackground(new java.awt.Color(242, 227, 202));
        jPanel9.setLayout(null);

        jLabel18.setText("Product ID");
        jPanel9.add(jLabel18);
        jLabel18.setBounds(100, 100, 61, 17);

        jLabel19.setText("Price:");
        jPanel9.add(jLabel19);
        jLabel19.setBounds(15, 118, 50, 17);

        jLabel20.setText("Quantity:");
        jPanel9.add(jLabel20);
        jLabel20.setBounds(15, 143, 100, 17);

        jLabel21.setText("$$$");
        jPanel9.add(jLabel21);
        jLabel21.setBounds(49, 118, 60, 17);

        jSpinner4.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel9.add(jSpinner4);
        jSpinner4.setBounds(68, 140, 64, 23);

        jButton11.setBackground(new java.awt.Color(102, 51, 0));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("Add to Cart");
        jButton11.addActionListener(this::jButton11ActionPerformed);
        jPanel9.add(jButton11);
        jButton11.setBounds(10, 180, 222, 34);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/99.png"))); // NOI18N
        jLabel22.setText("jLabel7");
        jPanel9.add(jLabel22);
        jLabel22.setBounds(70, 0, 100, 100);

        jLabel37.setText("Stock:");
        jPanel9.add(jLabel37);
        jLabel37.setBounds(160, 120, 40, 17);

        jLabel42.setText("#");
        jPanel9.add(jLabel42);
        jLabel42.setBounds(200, 120, 43, 17);

        jPanel5.add(jPanel9);
        jPanel9.setBounds(680, 320, 240, 220);

        jPanel10.setBackground(new java.awt.Color(242, 227, 202));
        jPanel10.setLayout(null);

        jLabel23.setText("Product ID");
        jPanel10.add(jLabel23);
        jLabel23.setBounds(100, 100, 61, 17);

        jLabel24.setText("Price:");
        jPanel10.add(jLabel24);
        jLabel24.setBounds(15, 118, 50, 17);

        jLabel25.setText("Quantity:");
        jPanel10.add(jLabel25);
        jLabel25.setBounds(15, 143, 110, 17);

        jLabel26.setText("$$$");
        jPanel10.add(jLabel26);
        jLabel26.setBounds(49, 118, 60, 17);

        jSpinner5.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel10.add(jSpinner5);
        jSpinner5.setBounds(68, 140, 64, 23);

        jButton12.setBackground(new java.awt.Color(102, 51, 0));
        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("Add to Cart");
        jButton12.addActionListener(this::jButton12ActionPerformed);
        jPanel10.add(jButton12);
        jButton12.setBounds(10, 180, 222, 34);

        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/10.png"))); // NOI18N
        jLabel27.setText("jLabel7");
        jPanel10.add(jLabel27);
        jLabel27.setBounds(70, 0, 100, 100);

        jLabel35.setText("Stock:");
        jPanel10.add(jLabel35);
        jLabel35.setBounds(160, 120, 40, 17);

        jLabel41.setText("#");
        jPanel10.add(jLabel41);
        jLabel41.setBounds(200, 120, 43, 17);

        jPanel5.add(jPanel10);
        jPanel10.setBounds(680, 80, 240, 220);

        jPanel11.setBackground(new java.awt.Color(242, 227, 202));
        jPanel11.setLayout(null);

        jLabel28.setText("Product ID");
        jPanel11.add(jLabel28);
        jLabel28.setBounds(90, 100, 80, 17);

        jLabel29.setText("Price:");
        jPanel11.add(jLabel29);
        jLabel29.setBounds(15, 118, 50, 17);

        jLabel30.setText("Quantity:");
        jPanel11.add(jLabel30);
        jLabel30.setBounds(15, 143, 90, 17);

        jLabel31.setText("$$$");
        jPanel11.add(jLabel31);
        jLabel31.setBounds(49, 118, 60, 17);

        jSpinner6.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel11.add(jSpinner6);
        jSpinner6.setBounds(68, 140, 64, 23);

        jButton13.setBackground(new java.awt.Color(102, 51, 0));
        jButton13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Add to Cart");
        jButton13.addActionListener(this::jButton13ActionPerformed);
        jPanel11.add(jButton13);
        jButton13.setBounds(10, 180, 222, 34);

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/classy/views/Product_List/8.png"))); // NOI18N
        jLabel32.setText("jLabel7");
        jPanel11.add(jLabel32);
        jLabel32.setBounds(70, 0, 100, 100);

        jLabel34.setText("Stock:");
        jPanel11.add(jLabel34);
        jLabel34.setBounds(160, 120, 40, 17);

        jLabel40.setText("#");
        jPanel11.add(jLabel40);
        jLabel40.setBounds(200, 120, 30, 17);

        jPanel5.add(jPanel11);
        jPanel11.setBounds(370, 80, 240, 220);

        jComboBox2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sizes", "Small", "Medium", "Large" }));
        jComboBox2.addActionListener(this::jComboBox2ActionPerformed);
        jPanel5.add(jComboBox2);
        jComboBox2.setBounds(389, 20, 83, 21);

        lblMeasurement.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMeasurement.setText("size measurements");
        lblMeasurement.addActionListener(this::lblMeasurementActionPerformed);
        jPanel5.add(lblMeasurement);
        lblMeasurement.setBounds(480, 20, 151, 21);

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 970, 560));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 988, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Open the Cart window
        CartJFrame cartPage = new CartJFrame();
        cartPage.setVisible(true);

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // Open the Product List window
        Product_ListJFrame_Pants productPage = new Product_ListJFrame_Pants();
        productPage.setVisible(true);

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Open the Pending Orders window
        OrdersJFrame orderPage = new OrdersJFrame();
        orderPage.setVisible(true);

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // Go back to Log In window
        LogInJFrame loginPage = new LogInJFrame();
        loginPage.setVisible(true);

        // Close the current window
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // Go to Shirts Panel
        Product_ListJFrame_Shirts productPage = new Product_ListJFrame_Shirts();
        productPage.setVisible(true);

        // Close the current panel
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // Pink Pants
        handleAddToCart("Pink Pants", jSpinner1);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // Go to Dresses Panel
        Product_ListJFrame_Dresses productPage = new Product_ListJFrame_Dresses();
        productPage.setVisible(true);

        // Close the current panel
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // Go to Pants Panel
        Product_ListJFrame_Pants productPage = new Product_ListJFrame_Pants();
        productPage.setVisible(true);

        // Close the current panel
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        // Black Pants
        handleAddToCart("Black Pants", jSpinner2);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        // White Pants
        handleAddToCart("White Pants", jSpinner3);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        // Denim
        handleAddToCart("Denim", jSpinner4);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        // Hiphop
        handleAddToCart("Hiphop", jSpinner5);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        // Star Pants
        handleAddToCart("Star Pants", jSpinner6);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void SizesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SizesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SizesActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        displayProductInfo();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void lblMeasurementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblMeasurementActionPerformed
        String selectedSize = jComboBox2.getSelectedItem().toString();

        if (selectedSize.equals("Sizes") || selectedSize.equals("Select")) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Please select a Size first.", "Size Description", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            // USE SHIRTS MODEL
            classy.models.Pants temp = new classy.models.Pants("Temp", 0, 0, selectedSize);

            javax.swing.JOptionPane.showMessageDialog(this, 
                "Measurements for " + selectedSize + ":\n\n" + temp.getMeasurementDescription(), 
                "Size Description", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_lblMeasurementActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Product_ListJFrame_Pants().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JSpinner jSpinner6;
    private javax.swing.JButton lblMeasurement;
    // End of variables declaration//GEN-END:variables
}
