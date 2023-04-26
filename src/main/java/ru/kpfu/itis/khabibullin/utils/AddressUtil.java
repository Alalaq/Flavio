package ru.kpfu.itis.khabibullin.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AddressUtil {
    private static final String GEOCODE_API_URL = "https://api.opencagedata.com/geocode/v1/json";
    private static final String API_KEY = "3113fa636f4d44e29faa3c1eb4553fce";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        System.out.println(calculateDistance("Tverskaya st. 1, Moscow", "Tverskaya Street, 15, Moscow"));
    }

    /**
     * Calculates the distance between two addresses using the Haversine formula.
     *
     * @param address1 the first address
     * @param address2 the second address
     * @return the distance in kilometers
     */

    public static double calculateDistance(String address1, String address2) {
        Coordinates coordinates1 = getCoordinates(address1);
        Coordinates coordinates2 = getCoordinates(address2);
        if (coordinates1 != null && coordinates2 != null) {
            double lat1 = coordinates1.latitude;
            double lon1 = coordinates1.longitude;
            double lat2 = coordinates2.latitude;
            double lon2 = coordinates2.longitude;
            int earthRadius = 6371; // km
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return earthRadius * c;
        } else {
            throw new IllegalArgumentException("Wrong address");
        }
    }

    public static Coordinates getCoordinates(String address) {
        try {
            String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
            URL url = new URL(GEOCODE_API_URL + "?key=" + API_KEY + "&q=" + encodedAddress);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                JsonNode response = objectMapper.readTree(connection.getInputStream());
                JsonNode results = response.get("results");
                if (results.isArray() && results.size() > 0) {
                    JsonNode result = results.get(0);
                    JsonNode geometry = result.get("geometry");
                    if (geometry != null) {
                        double latitude = geometry.get("lat").asDouble();
                        double longitude = geometry.get("lng").asDouble();
                        return new Coordinates(latitude, longitude);
                    }
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public record Coordinates(double latitude, double longitude) {
    }
}
