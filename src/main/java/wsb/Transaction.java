package wsb;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private double exchangeRate;
    private double quantity;
    private Date transactionDate;
    private SimpleDateFormat dateFormat;
    private String date;

    public Transaction(double exchangeRate, double quantity) {
        this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.exchangeRate = exchangeRate;
        this.quantity = quantity;
        this.transactionDate = new Date();
        this.date = dateFormat.format(transactionDate);

    }

    public double getAmount() {
        return exchangeRate;
    }

    public void setAmount(double amount) {
        this.exchangeRate = amount;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "quantity: " + quantity + ", value: " + exchangeRate + ", date of the transaction: " + date;
    }
}
