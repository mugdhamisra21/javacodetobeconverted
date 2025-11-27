import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CurrencyExchangeRatesFetcher {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // Free API for exchange rates

    public static void main(String[] args) {
        try {
            CurrencyExchangeRatesFetcher fetcher = new CurrencyExchangeRatesFetcher();
            Map<String, Double> rates = fetcher.fetchExchangeRates();

            if (rates != null && !rates.isEmpty()) {
                System.out.println("Currency Exchange Rates (Base: USD):");
                for (Map.Entry<String, Double> entry : rates.entrySet()) {
                    System.out.printf("%s: %.4f%n", entry.getKey(), entry.getValue());
                }
            } else {
                System.out.println("No rates fetched. Check API or connection.");
            }
        } catch (Exception e) {
            System.err.println("Error fetching rates: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, Double> fetchExchangeRates() throws Exception {
        Map<String, Double> rates = new HashMap<>();

        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new Exception("API error: HTTP " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

// Simple JSON parsing (standalone — no external libs)
        String jsonResponse = response.toString();
        int ratesStart = jsonResponse.indexOf("\"rates\":{");
        if (ratesStart == -1) {
            throw new Exception("Invalid API response");
        }

        String ratesJson = jsonResponse.substring(ratesStart + 8); // Skip "\"rates\":{"
        ratesJson = ratesJson.substring(0, ratesJson.lastIndexOf("}"));

        String[] ratePairs = ratesJson.split(",");
        for (String pair : ratePairs) {
            pair = pair.trim();
            if (pair.startsWith("\"") && pair.contains(":")) {
                String currency = pair.substring(1, pair.indexOf(":")).trim();
                String valueStr = pair.substring(pair.indexOf(":") + 1).trim();
                valueStr = valueStr.replaceAll("[^0-9.]", ""); // Simple cleanup
                double value = Double.parseDouble(valueStr);
                rates.put(currency, value);
            }
        }

        return rates;
    }
}
