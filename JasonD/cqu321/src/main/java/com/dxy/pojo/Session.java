package com.dxy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: JasonD
 * @date: 2023/3/16 17:36
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Integer id;
    private Integer year;
    private Boolean is_autumn;
}
