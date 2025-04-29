package org.kevinlin.springbootmall.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderServiceRedisTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void testStockDecrement() {
        String productKey = "stock:product123";

        // 初始化庫存
        redisTemplate.opsForValue().set(productKey, "10");

        // 模擬購買
        Long result = redisTemplate.opsForValue().decrement(productKey);

        // 驗證是否正常減少
        Assertions.assertEquals("9", redisTemplate.opsForValue().get(productKey));
    }

}