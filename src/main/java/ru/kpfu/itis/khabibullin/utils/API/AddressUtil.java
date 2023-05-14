/*
 * *
 *  Copyright (c) 2023.
 *  * @author Khabibullin Alisher (Alalaq)
 *  *
 *  * All rights are reserved by ITIS institute.
 *
 */

package ru.kpfu.itis.khabibullin.utils.API;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import okhttp3.*;
import ru.kpfu.itis.khabibullin.exceptions.IllegalAddressException;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
/**
 * @author Khabibullin Alisher
 */
public class AddressUtil {
    private static final String GEOCODE_API_URL = "https://api.opencagedata.com/geocode/v1/json";
    private static final String API_KEY_ENV_VAR_NAME = "OPENCAGE_API_KEY";
    private static final int EARTH_RADIUS = 6371; // km
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
            .build();

    private static final Cache<String, Coordinates> coordinatesCache = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    public static void main(String[] args) {
        System.out.println(AddressUtil.getCoordinates("Gabdulla Tukaya Street, 5 , Kazan")+ ", " + AddressUtil.getCoordinates("15 Akademika Glushko Street, Kazan"));
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
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                    + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.sin(dLon / 2) * Math.sin(dLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            return EARTH_RADIUS * c;
        } else {
            throw new IllegalAddressException("Wrong address");
        }
    }


    public static Coordinates getCoordinates(String address) {
        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }

        if (coordinatesCache.asMap().containsKey(address)) {
            return coordinatesCache.getIfPresent(address);
        }

        // T0D0: translate addresses so location would be more accurate

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(GEOCODE_API_URL))
                .newBuilder()
                .addQueryParameter("key", System.getenv(API_KEY_ENV_VAR_NAME))
                .addQueryParameter("q", address)
                .build();

        try (Response response = okHttpClient.newCall(new Request.Builder().url(url).get().build()).execute()) {
            if (response.isSuccessful()) {
                JsonNode responseBody = objectMapper.readTree(Objects.requireNonNull(response.body()).byteStream());
                JsonNode results = responseBody.get("results");
                if (results.isArray() && results.size() > 0) {
                    for (JsonNode result : results) {
                            JsonNode geometry = result.get("geometry");
                            if (geometry != null) {
                                double latitude = geometry.get("lat").asDouble();
                                double longitude = geometry.get("lng").asDouble();
                                Coordinates coordinates = new Coordinates(latitude, longitude);
                                coordinatesCache.put(address, coordinates);
                                return coordinates;
                            }
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
