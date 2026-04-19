package com.sky.service.impl;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.JsonObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //微信接口地址
public static final String WX_LOGIN="https://api.weixin.qq.com/sns/jscode2session";
@Autowired
private WeChatProperties weChatProperties;
@Autowired
private UserMapper userMapper;
/**
 * 微信登录方法实现
 * @param userLoginDTO 用户登录数据传输对象，包含微信登录所需的信息
 * @return User 返回登录后的用户信息，当前实现返回null
 */
    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) throws LoginException {
log.info("UserService{}",userLoginDTO);
        String openid = this.getOpenid(userLoginDTO.getCode());
        //判断openid是否有效
if(openid==null){
    throw new LoginException(MessageConstant.LOGIN_FAILED);
}
        //判断当前用户是否为新用户
User user=userMapper.selectByOpenid(openid);
        //如果为新用户，则创建用户信息并保存到数据库
        if(user==null)
        {
user= User.builder().openid(openid).createTime(LocalDateTime.now()).build();
userMapper.insert(user);
        }
        //如果为老用户，则直接返回用户信息
        return user;  // 当前方法实现为空，直接返回null
    }


    private String getOpenid(String code){
        // 调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid", weChatProperties.getAppid());
        map.put("secret", weChatProperties.getSecret());
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);

        JSONObject jsonObject = JSONUtil.parseObj(json);
        String openid = jsonObject.getStr("openid");
        return openid;
    }
}
