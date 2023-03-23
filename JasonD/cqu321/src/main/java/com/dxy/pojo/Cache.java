package com.dxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/3/21 14:45
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cache {
    private String key;
    private Object data;
}
