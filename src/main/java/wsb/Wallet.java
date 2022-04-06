package wsb;


import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Wallet {
    // Declaring field to help out with the methods created

    public CurrencyAPIConnector currencyAPIConnector;
    private String walletOwner;
    Map<Currencies, Transaction> userWallet;
    private DataBaseConnector connector;
    private static final String UPDATE_DATABASE_QUERY = "SELECT  date, currency, amount, exchangerate, id FROM public.transaction where wallet = true order by date desc";
    private SimpleDateFormat simpleDateFormat;
    private Currencies previousCurrency;

    /**
     * The database is already updated with one transaction and currency inside the wallet
     * **/


    // Create a public constructor to help to initialize some og the field when an instance on the class Wallet is created
    public Wallet() {
        this.userWallet = new ConcurrentHashMap<>();
        this.currencyAPIConnector = new CurrencyAPIConnector();
        //this.purchaseCurrency(Currencies.EUR, 2500, new Date());
        this.connector = new DataBaseConnector();
        this.simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
    }

    public String getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(String walletOwner) {
        this.walletOwner = walletOwner;
    }

    // Create the a function to purchase the currency passing the Enum and the Quantity

    public void purchaseCurrency(Currencies currency, double quantity) {
        // todo: latestValue commented out due to over reached the daily request limit, I have declared a default value just to test out the logic
        //double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject(currency.toString()).getDouble("value");
        double latestValue = 4.22;
        // Loop into the through the userWallet and check if the currency is inside the Map, If it is present, we assign to currency into the
        //variable previous currency
        for (Currencies key : userWallet.keySet()) {
            if (currency == key){
                this.previousCurrency = key;
            }
        }

        //In the case the user wants to buy a currency already in our wallet we will sum the quantity of the quantity purchased and the quantity already in the wallet

        if (userWallet.containsKey(currency)) {

            // We get the previous amount and store inside a variable
            double previousAmount = userWallet.get(currency).getAmount();

            //I created two queries, one to update the database, it will set the into the wallet to false for the previous currency and previous amount without changing the purchase state of the transaction
            String query2 = "UPDATE transaction SET wallet = FALSE WHERE currency = '" +this.previousCurrency +"'" + " and amount = " + previousAmount;
            // I insert the transaction into the database summing the previous quantity with the new one and set the iinto the wallet to true and set the swap to Purchase
            String query = "insert into transaction(currency, amount, exchangerate, date, wallet, swap) values ('" + currency + "', '" + (userWallet.get(currency).getAmount() + quantity) + "', '" + latestValue + "', '" + new Date() + "', 'TRUE', 'Purchase')";

            // Execute the two queries
            connector.insertUpdateQuery(query);
            connector.insertUpdateQuery(query2);
            // Update the wallet Map with the new transaction and currency
            userWallet.put(currency, new Transaction(latestValue, userWallet.get(currency).getAmount() + quantity, simpleDateFormat.format(new Date())));
            //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");

        } else {

            //If the currency is not in the wallet, we insert the transaction into the database as in the before statement and put the currency and transaction into the wallet map
            String query = "insert into transaction(currency, amount, exchangerate, date, wallet, swap) values ('" + currency + "', '" + quantity + "', '" + latestValue + "', '" + new Date() + "', 'TRUE', 'Purchase')";
            connector.insertUpdateQuery(query);
            userWallet.put(currency, new Transaction(latestValue, quantity, simpleDateFormat.format(new Date())));
            //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");

            //Print out that the transaction has been processed successfully
            System.out.println("Currency: " + currency + " purchased successfully");

        }

    }

    // Sell currency providing the old currency and the currency that the user wants to buy

    public void sellCurrency(Currencies currencies, Currencies toExchange) {

        // We interate through, I have converted the Map to Set to avoid concurrency exception
        Set<Map.Entry<Currencies, Transaction>> entrySet = userWallet.entrySet();
        for (Map.Entry<Currencies, Transaction> entry : entrySet) {

            //We check if the currency is inside the wallet
            if (entry.getKey() == currencies) {

                //If the currency is inside the wallet we proceesed to purchase the new currency calling the method PurchaseCurrency
                purchaseCurrency(toExchange, userWallet.get(entry.getKey()).getAmount());
                //It will update the wallet status for the old currency.
                String query = "UPDATE transaction SET wallet = FALSE,  swap = 'Sold' WHERE currency = '" + currencies + "'";
                //Execute the query
                connector.insertUpdateQuery(query);
                // We will print out that the currency has been sold successfully
                System.out.println("Currency: " + currencies + " sold successfully");
                //We update the userWallet map removing the old currency
                userWallet.remove(currencies);
                //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");
                return;

            }

        }

        //If the old currency i not inside the userWallet map it will print out a message
        System.out.println("The currency does not belong to your wallet");

    }


    //Help function that print out the entry of our userWallet
    public void printWallet() {
        if (userWallet.size() > 0) {
            for (Currencies key : userWallet.keySet()) {
                System.out.println("Currency: " + key + ", " + userWallet.get(key));
            }
        } else {
            System.out.println("The wallet is empty");
        }
    }

    // I have created a method that load the trasaction from our database
    public void loadDatabaseTransaction() {
        DataBaseConnector dataBaseConnector = new DataBaseConnector();

        try {

            //create the result set and pass the query
            ResultSet rs = dataBaseConnector.selectQuery(UPDATE_DATABASE_QUERY);
            //Interate through our result set and store each entry into the variables and put the transaction inside the userWallet
            while (rs.next()) {
                String date1 = rs.getString("date");
                String currency = rs.getString("currency");
                double quantity = rs.getDouble("amount");
                double exchangeRate = rs.getDouble("exchangerate");
                userWallet.put(Currencies.valueOf(currency), new Transaction(exchangeRate, quantity, date1));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
