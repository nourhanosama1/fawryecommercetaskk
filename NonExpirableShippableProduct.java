public class NonExpirableShippableProduct extends Product implements Shippable {
    private double weightInKg;

    public NonExpirableShippableProduct(String name, double price, int quantity, double weightInKg) {
        super(name, price, quantity);
        this.weightInKg = weightInKg;
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
