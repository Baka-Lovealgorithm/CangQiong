package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //根据openid查询用户信息
    @Select("SELECT * FROM user WHERE openid = #{openid}")
    User selectByOpenid(String openid);

/**
 * 向数据库中插入一个用户信息的方法
 * @param user 包含用户信息的User对象
 */
    void insert(User user);  // 方法声明：向数据库插入用户信息
}
