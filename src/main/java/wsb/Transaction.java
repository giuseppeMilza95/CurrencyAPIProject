package wsb;

import java.util.Date;

public class Transaction {
    private double exchangeRate;
    private double amount;
    private String transactionDate;


    public Transaction(double exchangeRate, double amount, String transactionDate) {

        this.exchangeRate = exchangeRate;
        this.amount = amount;
        this.transactionDate = transactionDate;

    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "quantity: " + amount + ", value: " + exchangeRate + ", date of the transaction: " + transactionDate;
    }
}
