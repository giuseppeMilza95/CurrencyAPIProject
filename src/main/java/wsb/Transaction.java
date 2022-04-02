package wsb;

public class Transaction {
    private double amount;
    private double quantity;

    public Transaction(double amount, double quantity) {
        this.amount = amount;
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
