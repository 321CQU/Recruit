package com.dxy.pojo;

import lombok.Data;

/**
 * @Author: JasonD
 * @date: 2023/3/15 09:36
 * @Description:
 */
@Data
public class Token {
    private String apiKey = "";
    private String key;
    private int tokenExpireTime;
    private int refreshTokenExpireTime;
}
