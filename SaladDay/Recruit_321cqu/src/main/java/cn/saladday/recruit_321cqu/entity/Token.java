package cn.saladday.recruit_321cqu.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Token {
    private static final long serialVersionUID = 1L;
    @TableId
    String userId;
    String token;
    String refreshToken;
    Integer tokenExpireTime;
    Integer refreshTokenExpireTime;
}
