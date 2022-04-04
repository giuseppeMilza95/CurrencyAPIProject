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
        wallet.loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true order by date desc");
        //wallet.purchaseCurrency(Currencies.CAD, 2000.0);
        //wallet.purchaseCurrency(Currencies.CAD, 2000.0);
//        wallet.purchaseCurrency(Currencies.PLN, 2500.0);
//        wallet.printWallet();
//        wallet.sellCurrency(Currencies.CAD, Currencies.USD);
        //wallet.printWallet();
        //wallet.loadDatabaseTransaction("SELECT date, currency, amount, id FROM public.transaction;");
        System.out.println("---------Uploading the currencies fro  database: ");

        wallet.printWallet();
        System.out.println("-------------purchasing cuurencies---------------");
        //wallet.purchaseCurrency(Currencies.CAD, 2000.0);
        //wallet.purchaseCurrency(Currencies.EUR, 3000.0);
        //wallet.purchaseCurrency(Currencies.CAD, 1000.0);
        //wallet.purchaseCurrency(Currencies.JPY, 15000.0);
        //wallet.purchaseCurrency(Currencies.PLN, 4000.0);
        //wallet.printWallet();
        System.out.println("------------selling currencies ---------");
        wallet.sellCurrency(Currencies.CAD, Currencies.EUR);
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
