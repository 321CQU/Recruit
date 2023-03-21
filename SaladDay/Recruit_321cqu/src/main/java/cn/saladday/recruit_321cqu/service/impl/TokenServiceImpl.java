package cn.saladday.recruit_321cqu.service.impl;

import cn.saladday.recruit_321cqu.dao.TokenMapper;
import cn.saladday.recruit_321cqu.entity.Token;
import cn.saladday.recruit_321cqu.service.TokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {
}
