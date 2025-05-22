package org.kevinlin.springbootmall.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
        Assertions.assertEquals(9, result);
    }

    @Test
    public void testConcurrentDecrement() throws InterruptedException {
        // Given：初始化商品庫存為 5
        String productKey = "stock:product456";
        redisTemplate.opsForValue().set(productKey, "5");

        // When：有 10 個人同時購買商品
        AtomicInteger successCount = buyProduct(productKey, 10);


        // Then：最多只有 5 筆成功扣庫存
        Assertions.assertEquals(5, successCount.get());
    }


    private AtomicInteger buyProduct(String productKey, int threadCount) throws InterruptedException {


        // 建立一個固定大小為 10 的執行緒池
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // 使用 CountDownLatch 等待所有執行緒完成任務後再繼續
        CountDownLatch latch = new CountDownLatch(threadCount);

        // 使用 AtomicInteger 統計成功購買（成功扣減庫存）的次數，確保執行緒安全
        AtomicInteger successCount = new AtomicInteger(0);

        // 模擬同時有 10 個執行緒進行購買行為（例如秒殺商品）
        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> attemptDecrement(productKey, successCount, latch));
        }

        // 主執行緒等待所有購買執行緒都完成（CountDownLatch 倒數為 0）
        latch.await();
        return successCount;
    }

    private void attemptDecrement(String key, AtomicInteger successCount, CountDownLatch latch) {
        try {
            // 嘗試扣庫存
            Long stock = redisTemplate.opsForValue().decrement(key);
            if (stock != null && stock >= 0) {
                successCount.incrementAndGet(); // 成功扣庫存
            } else {
                // 若失敗（扣成負數），則回補
                redisTemplate.opsForValue().increment(key);
            }
        } finally {
            latch.countDown(); // 執行緒完成後計數器減一
        }
    }



}