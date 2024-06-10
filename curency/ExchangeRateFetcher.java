package curency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateFetcher {

    private static final String API_KEY = "9f2e5299a05b7720bb69c066"; // Replace with your actual API key
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static void main(String[] args) {
        String baseCurrency = "USD"; // Replace with your base currency
        String targetCurrency = "EUR"; // Replace with your target currency
        String exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
        System.out.println("Exchange rate from " + baseCurrency + " to " + targetCurrency + ": " + exchangeRate);
    }

    public static String getExchangeRate(String baseCurrency, String targetCurrency) {
        String urlString = BASE_URL + API_KEY + "/latest/" + baseCurrency;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) { // Success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                conn.disconnect();

                // Parse JSON response to extract exchange rate
                int start = response.indexOf("\"" + targetCurrency + "\":") + targetCurrency.length() + 3;
                int end = response.indexOf(",", start);
                if (end == -1) {
                    end = response.indexOf("}", start);
                }
                String exchangeRate = response.substring(start, end);

                return exchangeRate;
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
}


