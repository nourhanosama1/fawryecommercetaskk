import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            // Create products
            Product cheese = new ExpirableShippableProduct("Cheese", 100, 10, LocalDate.of(2025, 8, 1), 0.2); // 200g
            Product biscuits = new ExpirableShippableProduct("Biscuits", 150, 5, LocalDate.of(2025, 8, 1), 0.7); // 700g
            Product tv = new NonExpirableShippableProduct("TV", 300, 5, 5.0);
            Product scratchCard = new SimpleProduct("ScratchCard", 50, 20); // not shippable

            // Create customer with a balance
            Customer customer = new Customer(1000);

            // Add products to cart
            customer.addToCart(cheese, 2);
            customer.addToCart(biscuits, 1);
            customer.addToCart(scratchCard, 1);

            // Checkout
            customer.checkout();

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
}
