package wsb;

import org.json.JSONObject;

public class Main {

    public static void main(String[] args) {
        CurrencyAPIConnector connector = new CurrencyAPIConnector();
        System.out.println(connector.getLatestData());

        getExchangeHistorical("2022-03-07", 2000.0);



    }

    public static void getExchangeHistorical(String purchaseDate, double amount){
        CurrencyAPIConnector currencyAPIConnector = new CurrencyAPIConnector();
        double historicalValue = currencyAPIConnector.getHistoricalData("USD", purchaseDate).getJSONObject("data").getJSONObject("PLN").getDouble("value");
        double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject("PLN").getDouble("value");
        if (historicalValue < latestValue){
            System.out.println("This is a good moment to sell USD and buy PLN, 1.0 USD is equal to: " + latestValue);
            System.out.println("You will earn: " + ((latestValue*amount) - (historicalValue*amount)) + "PLN");
        } else{
            System.out.println("This is not  a good moment to sell USD and buy PLN, 1.0 USD is equal to: " + historicalValue);
            System.out.println("You will loose: " + ((historicalValue*amount) - (latestValue*amount)) +"PLN");
        }



    }



}
