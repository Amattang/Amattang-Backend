package com.example.amattang.template;

import com.example.amattang.configuration.RedisConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@SpringBootTest(classes = {RedisCustomTemplate.class, RedisConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RedisCustomTemplateTest {

    @Autowired
    private RedisCustomTemplate redisCustomTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    @Order(1)
    @DisplayName("redis에 key-value 저장과 조회")
    public void saveValue() {
        redisCustomTemplate.timeoutTemplate("test", "value", 30, TimeUnit.SECONDS);
        String test = redisCustomTemplate.getRedisStringValue("test");
        assertThat(test).isEqualTo("value");
    }

    @Test
    @Order(2)
    @DisplayName("redis 삭제 테스트")
    public void deleteValue() {
        Boolean test = redisTemplate.hasKey("test");
        redisCustomTemplate.deleteRedisStringValue("test");
        Boolean test2 = redisTemplate.hasKey("test");
        assertThat(test).isEqualTo(true);
        assertThat(test2).isEqualTo(false);
    }

}