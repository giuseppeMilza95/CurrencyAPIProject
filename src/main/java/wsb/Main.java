package wsb;

import org.json.JSONObject;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        CurrencyAPIConnector connector = new CurrencyAPIConnector();
        //System.out.println(connector.getLatestData());
        System.out.println("*****************Task2******************");
        //getExchangeHistorical("2022-03-02", 2000.0);

        System.out.println("*****************Task3******************");
        Wallet wallet = new Wallet();
        wallet.purchaseCurrency(Currencies.CAD, 2000.0);
        wallet.purchaseCurrency(Currencies.CAD, 2000.0);
        wallet.purchaseCurrency(Currencies.PLN, 2500.0);
        wallet.printWallet();
        wallet.sellCurrency(Currencies.CAD, Currencies.USD);
        wallet.printWallet();
        System.out.println("*****************Task4******************");
//        try {
//            new DataBaseConnector().connect();
//        }catch (SQLException e){
//            e.printStackTrace();
//        }

//        try {
//
//            String query = "insert into transaction values(1,'2022-01-01', 'PLN', '1000')";
//            new DataBaseConnector().execute(query);
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
