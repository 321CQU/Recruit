package controller;

import utils.JsonUtils;
import utils.OkHttpApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class GetData {
    public static void getScore() throws IOException {
        OkHttpApi api = new OkHttpApi();
        String run = api.run("https://api.321cqu.com/v1/recruit/score");
        //防止乱码
        byte[] bytes = run.getBytes(StandardCharsets.UTF_8);
        String jsonStringWithCharset = new String(bytes, StandardCharsets.ISO_8859_1);
        JsonUtils.parseJson(jsonStringWithCharset);
    }
}
