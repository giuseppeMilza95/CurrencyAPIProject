package wsb;


import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Wallet {
    public CurrencyAPIConnector currencyAPIConnector;
    private String walletOwner;
    Map<Currencies, Transaction> userWallet;
    private DataBaseConnector connector;
    private static final String UPDATE_DATABASE_QUERY = "SELECT  date, currency, amount, exchangerate, id FROM public.transaction where wallet = true order by date desc";



    public Wallet() {
        this.userWallet = new ConcurrentHashMap<>();
        this.currencyAPIConnector = new CurrencyAPIConnector();
        //this.purchaseCurrency(Currencies.EUR, 2500, new Date());
        this.connector = new DataBaseConnector();
    }

    public String getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(String walletOwner) {
        this.walletOwner = walletOwner;
    }

    public void purchaseCurrency(Currencies currency, double quantity) {
        // todo: latestValue commented out due to over reached the daily request limit
        //double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject(currency.toString()).getDouble("value");
        double latestValue = 4.22;
        if (userWallet.containsKey(currency)) {
            String query = "insert into transaction(currency, amount, exchangerate, date, wallet, swap) values ('" + currency + "', '" + (userWallet.get(currency).getAmount() + quantity) + "', '" + latestValue + "', '" + new Date() + "', 'TRUE', 'Purchase')";
            connector.insertUpdateQuery(query);
            userWallet.put(currency, new Transaction(latestValue, userWallet.get(currency).getAmount() + quantity, new Date()));
            //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");

        } else {
            String query = "insert into transaction(currency, amount, exchangerate, date, wallet, swap) values ('" + currency + "', '" + quantity + "', '" + latestValue + "', '" + new Date() + "', 'TRUE', 'Purchase')";
            connector.insertUpdateQuery(query);
            userWallet.put(currency, new Transaction(latestValue, quantity, new Date()));
            //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");
            System.out.println("Currency: " + currency + " purchased successfully");

        }

    }


    public void sellCurrency(Currencies currencies, Currencies toExchange) {
        Set<Map.Entry<Currencies, Transaction>> entrySet = userWallet.entrySet();
        for (Map.Entry<Currencies, Transaction> entry : entrySet) {
            if (entry.getKey() == currencies) {

                purchaseCurrency(toExchange, userWallet.get(entry.getKey()).getAmount());
                String query = "UPDATE transaction SET wallet = FALSE,  swap = 'Sold' WHERE currency = '" + currencies + "'";
                connector.insertUpdateQuery(query);
                System.out.println("Currency: " + currencies + " sold successfully");
                userWallet.remove(currencies);
                //loadDatabaseTransaction("SELECT  date, currency, amount, id FROM public.transaction where wallet = true");
                return;

            }

        }
        System.out.println("The currency does not belong to your wallet");

    }

    public void printWallet() {
        if (userWallet.size() > 0) {
            for (Currencies key : userWallet.keySet()) {
                System.out.println("Currency: " + key + ", " + userWallet.get(key));
            }
        } else {
            System.out.println("The wallet is empty");
        }
    }


    public void loadDatabaseTransaction() {
        DataBaseConnector dataBaseConnector = new DataBaseConnector();

        try {
            ResultSet rs = dataBaseConnector.selectQuery(UPDATE_DATABASE_QUERY);
            while (rs.next()) {
                Date date1 = rs.getDate("date");
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
