/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.models;

public class Shirts extends Product {
    
    // 1. New Variables for Measurements
    private double chestWidth;
    private double bodyLength;

    public Shirts(String name, int quantity, double price, String size) {
        super(name, quantity, price, size);
        calculateMeasurements(); // 2. Calculate logic automatically
    }

    // 3. Logic: Assigns measurements based on Size
    private void calculateMeasurements() {
        switch (this.getSize()) {
            case "Small": 
                this.chestWidth = 36.0; 
                this.bodyLength = 28.0; 
                break;
            case "Medium": 
                this.chestWidth = 40.0; 
                this.bodyLength = 29.0; 
                break;
            case "Large": 
                this.chestWidth = 44.0; 
                this.bodyLength = 30.0; 
                break;
            default: 
                this.chestWidth = 0; 
                this.bodyLength = 0;
        }
    }

    // 4. Getter for the Description (The View calls this!)
    public String getMeasurementDescription() {
        return "Chest: " + chestWidth + "\" \nLength: " + bodyLength + "\"";
    }
}