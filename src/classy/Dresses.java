package classy;

/**
 *
 * @author milal
 */
public class Dresses extends Product {
    
    // Updated to match the 6-parameter Product constructor
    public Dresses(String name, double price, int total, int s, int m, int l) {
        
        // calls the constructor already written in the Product.java
        super(name, price, total, s, m, l);
    }
}