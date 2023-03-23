package com.wyn.utils;

import com.alibaba.fastjson2.JSONArray;
import com.wyn.pojo.Score;

import java.util.List;


public class JsonUtils {
    public static List<Score> parseJson(String jsonString) {
        JSONArray dataArray = JSONArray.parseArray(jsonString);
        return dataArray.toJavaList(Score.class);
    }
}

