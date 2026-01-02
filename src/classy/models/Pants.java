/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package classy.models;

public class Pants extends Product {
    
    // 1. New Variables for Pants
    private int waist;
    private int inseam;

    public Pants(String name, int quantity, double price, String size) {
        super(name, quantity, price, size);
        calculateMeasurements(); // 2. Calculate logic automatically
    }

    // 3. Logic: Assigns measurements based on Size
    private void calculateMeasurements() {
        switch (this.getSize()) {
            case "Small": 
                this.waist = 28; 
                this.inseam = 30; 
                break;
            case "Medium": 
                this.waist = 32; 
                this.inseam = 31; 
                break;
            case "Large": 
                this.waist = 36; 
                this.inseam = 32; 
                break;
            default: 
                this.waist = 0; 
                this.inseam = 0;
        }
    }

    // 4. Getter for the Description (The View calls this!)
    public String getMeasurementDescription() {
        return "Waist: " + waist + "\" \nInseam: " + inseam + "\"";
    }
}
