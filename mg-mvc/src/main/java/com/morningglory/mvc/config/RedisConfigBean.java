package com.morningglory.mvc.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * @Author: qianniu
 * @Date: 2019-09-04 19:59
 * @Desc:
 */
@Configuration
@Slf4j
public class RedisConfigBean {

    @Resource
    private Environment environment;

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig redisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public JedisConnectionFactory connectionFactory(JedisPoolConfig redisConfig){
        JedisConnectionFactory factory = new JedisConnectionFactory(redisConfig);

        String hostName = StringUtils.isEmpty(this.environment.getProperty("spring.redis.host"))
                ? "localhost" : this.environment.getProperty("spring.redis.host");

        String port = StringUtils.isEmpty(this.environment.getProperty("spring.redis.port"))
                ? "6379" : this.environment.getProperty("spring.redis.port");

        String password = this.environment.getProperty("spring.redis.password");

        String database = StringUtils.isEmpty(this.environment.getProperty("spring.redis.database"))
                ? "0" : this.environment.getProperty("spring.redis.database");

        factory.setHostName(hostName);
        factory.setPort(Integer.valueOf(port));
        factory.setPassword(password);
        factory.setDatabase(Integer.valueOf(database));
        log.info("redis initialized with property host:[{}],pwd:[{}],database:[{}]",hostName, password,database);
        return factory;
    }

    @Bean
    @ConditionalOnMissingBean(value = {RedisTemplate.class})
    public RedisTemplate redisTemplate(JedisConnectionFactory connectionFactory) {

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();

        GenericFastJsonRedisSerializer jsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }
}
