package org.kevinlin.springbootmall.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private final Snowflake snowflake;
    public IdGenerator() {
        long workerId = 1L;
        long dataCenterId = 1L;
        this.snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
    }

    public String generateId() {
        return snowflake.nextIdStr();
    }
}