import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class currencyconvertor {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    // Function to fetch exchange rates from the API
    public static String fetchExchangeRates(String baseCurrency) throws Exception {
        String urlString = API_URL + baseCurrency;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder response;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }

        return response.toString();
    }

    // Function to extract exchange rate from JSON response
    public static double extractExchangeRate(String jsonResponse, String targetCurrency) {
        String ratesKey = "\"rates\":{";
        int ratesStart = jsonResponse.indexOf(ratesKey);
        if (ratesStart == -1) return -1;  // Key not found

        String ratesSection = jsonResponse.substring(ratesStart + ratesKey.length());
        int ratesEnd = ratesSection.indexOf("}");
        if (ratesEnd == -1) return -1;  // Section not found

        String ratesString = ratesSection.substring(0, ratesEnd);
        String[] ratesEntries = ratesString.split(",");

        for (String entry : ratesEntries) {
            String[] keyValue = entry.split(":");
            if (keyValue.length == 2) {
                String currencyCode = keyValue[0].replaceAll("\"", "").trim();
                if (currencyCode.equals(targetCurrency)) {
                    try {
                        return Double.parseDouble(keyValue[1].trim());
                    } catch (NumberFormatException e) {
                        return -1;  // Invalid number format
                    }
                }
            }
        }

        return -1;  // Currency not found
    }

    // Function to convert amount using the exchange rate
    public static double convertCurrency(double amount, double rate) {
        return amount * rate;
    }

    public static void main(String[] args) {
        try {
            try (// User input
            java.util.Scanner scanner = new java.util.Scanner(System.in)) {
                System.out.print("Enter the base currency (e.g., USD): ");
                String baseCurrency = scanner.nextLine().toUpperCase();
                System.out.print("Enter the target currency (e.g., EUR): ");
                String targetCurrency = scanner.nextLine().toUpperCase();
                System.out.print("Enter the amount to convert: ");
                double amount = scanner.nextDouble();
                scanner.close();

                // Fetch exchange rates
                String response = fetchExchangeRates(baseCurrency);
                double rate = extractExchangeRate(response, targetCurrency);

                if (rate != -1) {
                    double convertedAmount = convertCurrency(amount, rate);
                    System.out.printf("%.2f %s is equivalent to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("Target currency not found in the exchange rates.");
                }
            }

        } catch (Exception e) {
            System.out.println("An error occurred while fetching or processing the data.");
        }
    }
}
