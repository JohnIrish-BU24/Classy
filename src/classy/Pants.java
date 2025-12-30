package classy;

/**
 * @author milal
 */
public class Pants extends Product {
    
    // Updated to match the 6-parameter Product constructor
    public Pants(String name, double price, int total, int s, int m, int l) {
        // Calls the constructor in Product.java with all stock levels
        super(name, price, total, s, m, l);
    }
}