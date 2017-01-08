package packt.java9.by.example.mybusiness.product;

public class ProductDoesNotExists extends Exception {
    public ProductDoesNotExists(Product product) {
        super(product.toString());
    }
}
