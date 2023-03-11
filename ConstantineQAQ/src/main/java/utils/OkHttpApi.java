package utils;

import controller.GetToken;
import okhttp3.*;

import java.io.IOException;


public class OkHttpApi {
    OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json");

    public OkHttpApi() throws IOException {
    }

    //Get请求
    public String run(String url) throws IOException {
        String token = GetToken.getToken();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization","Bearer "+token)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    //Post请求
    public String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
