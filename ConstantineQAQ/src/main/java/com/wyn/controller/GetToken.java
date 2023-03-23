package com.wyn.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.wyn.utils.OkHttpUtils;

import java.io.IOException;

public class GetToken {
    public static String getToken() throws IOException {
        OkHttpUtils api = new OkHttpUtils();
        JSONObject object = new JSONObject(){{
            //gitGuardian会检测密码所以我删了
        }};
        String s = object.toString();
        String post = api.post("https://api.321cqu.com/v1/authorization/login", s);
        JSONObject jsonObject = JSONObject.parseObject(post);
        JSONArray data = jsonObject.getJSONArray("data");
        Object token = data.getJSONObject(0).get("token");
        return token.toString();
    }

}
