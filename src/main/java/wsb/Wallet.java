package wsb;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Wallet {
    public CurrencyAPIConnector currencyAPIConnector;
    private String walletOwner;
    Map<Currencies, Transaction> userWallet;

    public Wallet() {
        this.userWallet = new ConcurrentHashMap<>();
        this.currencyAPIConnector = new CurrencyAPIConnector();
        this.purchaseCurrency(Currencies.EUR, 2500);

    }

    public String getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(String walletOwner) {
        this.walletOwner = walletOwner;
    }

    public void purchaseCurrency(Currencies currency, double quantity){
        // todo: latestValue commented out due to over reached the daily request limit
        //double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject(currency.toString()).getDouble("value");
        double latestValue = 4.22;
        if (userWallet.containsKey(currency)){
            userWallet.put(currency, new Transaction(latestValue, userWallet.get(currency).getQuantity()+quantity));
        }else {
            userWallet.put(currency, new Transaction(latestValue, quantity));
            System.out.println("Currency: " + currency + " purchased successfully");
        }

    }


    public void sellCurrency(Currencies currencies, Currencies toExchange){
        Set<Map.Entry<Currencies, Transaction>> entrySet = userWallet.entrySet();
        for(Map.Entry<Currencies, Transaction> entry : entrySet){
            if (entry.getKey() == currencies){
                 //todo: add the transaction

                purchaseCurrency(toExchange, userWallet.get(entry.getKey()).getQuantity());
                userWallet.remove(currencies);
                System.out.println("Currency: " + currencies + " sold successfully");
            }

        }
    }

    public void printWallet(){
        if (userWallet.size() > 0) {
            for (Currencies key : userWallet.keySet()) {
                System.out.println("Currency: " + key + ", " + userWallet.get(key));

            }
        } else{
            System.out.println("The wallet is empty");

        }
    }




}
