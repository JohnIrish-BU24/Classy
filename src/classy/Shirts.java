package classy;

/**
 * @author milal
 */
public class Shirts extends Product {
    
    // Updated to match the 6-parameter constructor in Product.java
    public Shirts(String name, double price, int total, int s, int m, int l) {
        
        // calls the constructor in Product.java with the new inventory parameters
        super(name, price, total, s, m, l);
    }
}