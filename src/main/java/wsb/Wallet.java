package wsb;

import javax.swing.text.html.parser.Parser;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
    public CurrencyAPIConnector currencyAPIConnector;
    private String walletOwner;
    Map<Currencies, Transaction> wallet;

    public Wallet() {
        this.wallet = new HashMap<>();
        this.currencyAPIConnector = new CurrencyAPIConnector();

    }

    public String getWalletOwner() {
        return walletOwner;
    }

    public void setWalletOwner(String walletOwner) {
        this.walletOwner = walletOwner;
    }

    public void purchaseCurrency(Currencies currency, double quantity){
        double latestValue = currencyAPIConnector.getLatestData().getJSONObject("data").getJSONObject(currency.toString()).getDouble("value");
        wallet.put(currency, new Transaction(latestValue, quantity));
        System.out.println("Currency purchased");

    }


    public void sellCurrency(Currencies currencies){
        for(Currencies key : wallet.keySet()){
            if (key == currencies){
                wallet.remove(currencies);
                System.out.println("Currency sold");
            }

        }
    }

    public void printWallet(){
        if (wallet.size() > 0) {
            for (Currencies key : wallet.keySet()) {
                System.out.println("Currency: " + key + ", " + wallet.get(key));

            }
        } else{
            System.out.println("The wallet is empty");

        }
    }




}
