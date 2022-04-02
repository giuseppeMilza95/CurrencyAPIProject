package wsb;

import java.text.DateFormat;
import java.util.Date;

public class Transaction {
    private double value;
    private double quantity;
    private Date transactionDate;

    public Transaction(double value, double quantity) {
        this.value = value;
        this.quantity = quantity;
        this.transactionDate = new Date();
    }

    public double getAmount() {
        return value;
    }

    public void setAmount(double amount) {
        this.value = amount;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "quantity: " + quantity + ", value: " + value + ", date of the transaction: " + transactionDate;
    }
}
