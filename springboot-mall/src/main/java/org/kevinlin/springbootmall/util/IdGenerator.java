package org.kevinlin.springbootmall.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    //雪花算法
    private final Snowflake snowflake;
    public IdGenerator() {
        long workerId = 1L;
        long dataCenterId = 1L;
        this.snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
    }

    public long generateId() {
        return snowflake.nextId();
    }
}