import java.time.LocalDate;

public class ExpirableShippableProduct extends Product implements Shippable {
    private LocalDate expiryDate;
    private double weightInKg;

    public ExpirableShippableProduct(String name, double price, int quantity, LocalDate expiryDate, double weightInKg) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.weightInKg = weightInKg;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public boolean isShippable() {
        return true;
    }

    @Override
    public double getWeight() {
        return weightInKg;
    }
}
