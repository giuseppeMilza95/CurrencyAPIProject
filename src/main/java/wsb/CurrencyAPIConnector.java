package wsb;

import org.json.JSONObject;
import org.json.JSONTokener;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

public class CurrencyAPIConnector extends APIConnector {
    public static final String API_KEY = "lEHKQijwNSBoiEnAitSoiSilzJFZAI88lY6UI0kC";
    public static final String URL_ADDRESS = "https://freecurrencyapi.net/api/v3/";
    public static final String LATEST = "latest";
    public static final String HISTORICAL = "historical";

    public JSONObject getLatestData() {
        String data = getData(URL_ADDRESS + LATEST, API_KEY, null);
        JSONTokener tokener = new JSONTokener(data);
        JSONObject jsonCurrency = new JSONObject(tokener);
        return jsonCurrency;


    }

    public JSONObject getHistoricalData(String baseCurrency, String date) { //todo: change
        Map<String, String> parameters = new HashMap<>();
        parameters.put("base_currency", baseCurrency);
        parameters.put("date", date);

        String data = getData(URL_ADDRESS + HISTORICAL, API_KEY, parameters);
        JSONTokener tokener = new JSONTokener(data);
        JSONObject jsonCurrency = new JSONObject(tokener);
        return jsonCurrency;

    }
}




