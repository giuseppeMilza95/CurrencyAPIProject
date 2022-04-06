package wsb;

import org.json.JSONObject;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CurrencyAPIConnector connector = new CurrencyAPIConnector();
        //System.out.println(connector.getLatestData());
        System.out.println("*****************Task2******************");
        //getExchangeHistorical("2022-03-02", 2000.0);

        System.out.println("*****************Task3******************");
        Wallet wallet = new Wallet();
        System.out.println("---------Uploading the currencies fro  database: ");

        //Load the transactions
        wallet.loadDatabaseTransaction();

        //Print the wallet
        wallet.printWallet();
        System.out.println("-------------purchasing currencies---------------");

        //Purchasing USD
        wallet.purchaseCurrency(Currencies.USD, 25000);
        System.out.println("------------selling currencies ---------");

        //Selling USD for CAD
        wallet.sellCurrency(Currencies.USD, Currencies.CAD);

        //Print the wallet
        wallet.printWallet();

        //wallet.printWallet();
        System.out.println("*****************Task4******************");
        try {
            new DataBaseConnector().connect();
            System.out.println("Connected");
        }catch (SQLException e){
            e.printStackTrace();
        }

//        try {
//
//            String query = "insert into transaction(currency, amount, exchangerate, date) values('EUR', '2000', '0.8', '2022-04-03')";
//            new DataBaseConnector().insertQuery(query);
//        }catch (Exception e){
//            e.printStackTrace();
//        }







    }

    public static void getExchangeHistorical(String purchaseDate, double amount){
        CurrencyAPIConnector currencyAPIConnector = new CurrencyAPIConnector();
        double historicalValue = currencyAPIConnector.getHistoricalData("USD", purchaseDate).getJSONObject("data").getJSONObject("PLN").getDouble("value");
        double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject("PLN").getDouble("value");
        if (historicalValue < latestValue){
            System.out.println("This is a good moment to sell USD and buy PLN, 1.0 USD is equal to: " + latestValue);
            System.out.println("You will earn: " + ((latestValue*amount) - (historicalValue*amount)) + "PLN");
        } else{
            System.out.println("This is not  a good moment to sell USD and buy PLN, 1.0 USD is equal to: " + latestValue);
            System.out.println("You will loose: " + ((historicalValue*amount) - (latestValue*amount)) +"PLN");
        }

    }








}
