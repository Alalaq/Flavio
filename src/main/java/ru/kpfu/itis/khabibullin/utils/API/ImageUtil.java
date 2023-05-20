/*
 * *
 *  Copyright (c) 2023.
 *  * @author Khabibullin Alisher (Alalaq)
 *  *
 *  * All rights are reserved by ITIS institute.
 *
 */

package ru.kpfu.itis.khabibullin.utils.API;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ru.kpfu.itis.khabibullin.aspects.ExceptionHandler.handleException;

/**
 * @author Khabibullin Alisher
 */
public class ImageUtil {
    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos";
    private static final String API_KEY_ENV_VAR_NAME = "UNPLASH_API_KEY";

    public static List<String> getImages(String query, int count) {
        OkHttpClient client = new OkHttpClient();
        List<String> imageUrls = new ArrayList<>();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(UNSPLASH_API_URL)).newBuilder();
        urlBuilder.addQueryParameter("query", query);
        urlBuilder.addQueryParameter("per_page", String.valueOf(count));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Client-ID " + System.getenv(API_KEY_ENV_VAR_NAME))
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                IOException e = new IOException("Unexpected code " + response);
                handleException(e);
                throw e;
            }

            String responseBody = Objects.requireNonNull(response.body()).string();
            Gson gson = new Gson();
            ImageResponse imageResponse = gson.fromJson(responseBody, ImageResponse.class);


            for (Image image : imageResponse.getResults()) {
                imageUrls.add(image.getUrls().getRegular());
            }

        } catch (IOException e) {
            System.err.println("Failed to retrieve dish images: " + e.getMessage());
        }
        return imageUrls;
    }


    public static void main(String[] args) {
        System.out.println(getImages("Image for restaurant", 5));
    }

    @Getter
    @Setter
    private static class ImageResponse {
        private List<Image> results;
    }

    @Getter
    @Setter
    private static class Image {
        private ImageUrl urls;
    }

    @Getter
    @Setter
    private static class ImageUrl {
        private String regular;
    }
}
