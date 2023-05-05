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

public class ImageUtil {
    private static final String UNSPLASH_API_URL = "https://api.unsplash.com/search/photos";
    private static final String UNSPLASH_API_KEY = "ui0bPySHKmmnKoe6mkGW6MeUPj_ZBdYPwbtEjX1nspw";

    public static List<String> getImages(String query, int count) {
        OkHttpClient client = new OkHttpClient();
        List<String> imageUrls = new ArrayList<>();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UNSPLASH_API_URL).newBuilder();
        urlBuilder.addQueryParameter("query", query);
        urlBuilder.addQueryParameter("per_page", String.valueOf(count));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Client-ID " + UNSPLASH_API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
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
