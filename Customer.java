import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;


public class Customer {
    private double balance;
    private List<CartItem> cart;

    public Customer(double balance) {
        this.balance = balance;
        this.cart = new ArrayList<>();
    }

    public void addToCart(Product product, int quantity) throws Exception {
        if (quantity > product.getQuantity()) {
            throw new Exception("Not enough stock for " + product.getName());
        }

        cart.add(new CartItem(product, quantity));
    }

    public void checkout() throws Exception {
        if (cart.isEmpty()) throw new Exception("Cart is empty");

        double subtotal = 0;
        double shipping = 0;
        double totalWeightKg = 0;
        Map<String, Integer> shipmentCounts = new LinkedHashMap<>();
        Map<String, Integer> weightPerItem = new HashMap<>();
        StringBuilder receipt = new StringBuilder("** Checkout receipt **\n");

        for (CartItem item : cart) {
            Product p = item.getProduct();

            if (p.isExpired()) throw new Exception(p.getName() + " is expired");
            if (item.getQuantity() > p.getQuantity()) throw new Exception(p.getName() + " is out of stock");

            // Receipt line
            receipt.append(item.getQuantity()).append("x ").append(p.getName())
                    .append(" ").append((int) item.getTotalPrice()).append("\n");

            subtotal += item.getTotalPrice();

            if (p.isShippable() && p instanceof Shippable) {
                int count = shipmentCounts.getOrDefault(p.getName(), 0);
                shipmentCounts.put(p.getName(), count + item.getQuantity());

                int weight = (int)(((Shippable) p).getWeight() * 1000); // to grams
                weightPerItem.put(p.getName(), weight);
                totalWeightKg += ((Shippable) p).getWeight() * item.getQuantity();
            }
        }

        shipping = totalWeightKg * 10;
        double total = subtotal + shipping;

        if (balance < total) throw new Exception("Insufficient balance");

        for (CartItem item : cart) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        balance -= total;

        // Shipment notice
        if (!shipmentCounts.isEmpty()) {
            System.out.println("** Shipment notice **");
            for (String item : shipmentCounts.keySet()) {
                System.out.println(shipmentCounts.get(item) + "x " + item + " " + weightPerItem.get(item) + "g");
            }
            System.out.printf("Total package weight %.1fkg%n%n", totalWeightKg);
        }

        // Checkout receipt
        System.out.print(receipt);
        System.out.println("----------------------");
        System.out.println("Subtotal " + (int) subtotal);
        System.out.println("Shipping " + (int) shipping);
        System.out.println("Amount " + (int) total);

        cart.clear();
    }


    private double getTotalWeight(List<Shippable> items) {
        return items.stream().mapToDouble(Shippable::getWeight).sum();
    }
}
