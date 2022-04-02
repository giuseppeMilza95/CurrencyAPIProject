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

    }
}
