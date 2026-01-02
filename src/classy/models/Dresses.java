/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.models;

public class Dresses extends Product {
    
    // 1. New Variables for Dresses
    private String bust;
    private int length;

    public Dresses(String name, int quantity, double price, String size) {
        super(name, quantity, price, size);
        calculateMeasurements(); // 2. Calculate logic automatically
    }

    // 3. Logic: Assigns measurements based on Size
    private void calculateMeasurements() {
        switch (this.getSize()) {
            case "Small": 
                this.bust = "34"; 
                this.length = 36; 
                break;
            case "Medium": 
                this.bust = "38"; 
                this.length = 38; 
                break;
            case "Large": 
                this.bust = "42"; 
                this.length = 40; 
                break;
            default: 
                this.bust = "0"; 
                this.length = 0;
        }
    }

    // 4. Getter for the Description (The View calls this!)
    public String getMeasurementDescription() {
        return "Bust: " + bust + "\" \nLength: " + length + "\"";
    }
}
