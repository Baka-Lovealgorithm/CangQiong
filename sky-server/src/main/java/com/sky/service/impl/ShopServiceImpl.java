package com.sky.service.impl;

import com.sky.entity.Setmeal;
import com.sky.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void setBusinessStatus(Long status) {
        stringRedisTemplate.opsForValue().set("SHOP_STATUS",status.toString());
    }

    @Override
    public Integer getBusinessStatus() {
        String status = stringRedisTemplate.opsForValue().get("SHOP_STATUS");
        return Integer.parseInt(status);
    }


}
