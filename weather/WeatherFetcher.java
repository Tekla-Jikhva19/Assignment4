package weather;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherFetcher {

    private static final String API_KEY = "4900f5201b70e6622930e2380c0d7036"; // Replace with your actual API key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        String city = "London"; // Replace with your desired city
        String temperature = getTemperature(city);
        System.out.println("Temperature in " + city + ": " + temperature + "°C");
    }

    public static String getTemperature(String city) {
        String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY;
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

                // Parse JSON response to extract temperature
                int start = response.indexOf("\"temp\":") + 7;
                int end = response.indexOf(",", start);
                String temperature = response.substring(start, end);

                return temperature;
            } else {
                return "Error: " + responseCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
    }
}
