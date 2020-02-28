package com.morningglory.basic.cache.redis;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.google.common.collect.Sets;
import com.morningglory.basic.cache.redis.listener.RedisKeyExpirationEventMessageListener;
import com.morningglory.basic.cache.redis.serializer.KryoRedisSerializer;
import com.morningglory.basic.cache.redis.serializer.StringRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/*@Configuration
@EnableAutoConfiguration
@EnableCaching*/
@Slf4j
public class RedisConfig {

    @Value("${spring.redis.mode:standalone}")
    private String mode;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.sentinel.master:master}")
    private String master;

    @Value("${spring.redis.sentinel.nodes:127.0.0.1:6379}")
    private String hostAndPorts;


    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    public RedisSentinelConfiguration getRedisSentinelConfig(){
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration(master, Sets.newHashSet(hostAndPorts.split(";")));
        return sentinelConfiguration;
    }

    @Bean
    public RedisClusterConfiguration getRedisClusterConfig(){
        if(RedisModeConstant.CLUSTER.equals(mode)){
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(Sets.newHashSet(hostAndPorts.split(";")));
            return clusterConfiguration;
        }
        return null;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisPoolConfig config = getRedisConfig();
        //单机模式
        if(RedisModeConstant.STANDALONE.equals(mode)){
            JedisConnectionFactory factory = new JedisConnectionFactory(config);
            factory.setHostName(host);
            factory.setPort(port);
            factory.setPassword(password);
            log.info("pmp redis config mode :[{}],host :{}, port :{}",mode,host,port);
            return factory;
            //哨兵模式
        }else if(RedisModeConstant.SENTINEL.equals(mode)){
            JedisConnectionFactory factory = new JedisConnectionFactory(getRedisSentinelConfig(), config);
            factory.setPassword(password);
            log.info("pmp redis config mode :[{}],masterName:[{}],nodes:[{}],pwd:[{}]",mode,master, hostAndPorts,password);
            return factory;
            //集群模式
        }else if(RedisModeConstant.CLUSTER.equals(mode)){
            JedisConnectionFactory factory = new JedisConnectionFactory(getRedisClusterConfig(), config);
            factory.setPassword(password);
            log.info("pmp redis config mode :[{}],nodes:{},pwd:[{}]",mode, hostAndPorts,password);
            return factory;
        }
        return null;
    }

    @Bean("redisTemplate")
    public RedisTemplate<?, ?> getRedisTemplate(){
        // 自定义序列化方式
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getConnectionFactory());

        // 序列化后的格式 \x01\x00com.springboot.demo.pojo.Use\xF2\x80\x01\x04\x00ns\xE8\x80\x01\x01java.util.Dat\xE5\x97\xFC\x8B\xF0\xA6-\x80
        KryoRedisSerializer kryoRedisSerializer = new KryoRedisSerializer(Object.class);

        // 序列化成字符串 {id:3,name:nsh,time:1556622998970}
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 需要指定class类型，否则反序列化会失败
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        // 在序列化的时候将class类型也作为数据写进去了，返序列化时直接冲数据中取就行。可以用 {\"@type\":\"User\",\"id\":2,\"name\":\"nsh\",\"time\":1556623670124}
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();


        // 全局开启AutoType，不建议使用
        //ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // 建议使用这种方式，小范围指定白名单
        //ParserConfig.getGlobalInstance().addAccept("com.xiaolyuh.");

        // 设置值（value）的序列化采用FastJsonRedisSerializer。
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        // 设置键（key）的序列化采用StringRedisSerializer。
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //redisTemplate.afterPropertiesSet();
        return redisTemplate;


        /*RedisTemplate<?,?> redisTemplate = new StringRedisTemplate(getConnectionFactory());
        return redisTemplate;*/
    }

    @Bean
    public RedisMessageListenerContainer getListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(getConnectionFactory());
        return container;
    }

    @Bean
   public RedisKeyExpirationEventMessageListener getMessageListener(){
        return new RedisKeyExpirationEventMessageListener(this.getListenerContainer());
    }

    @Bean("getJedisPool")
    public Jedis getJedisPool(){
        JedisPoolConfig config = getRedisConfig();
        JedisPool jedisPool = new JedisPool(config, "127.0.0.1", 6379, 1000, "123456");
        return jedisPool.getResource();
    }

    //@Bean("getDevJedisPool")
    public Jedis getDevJedisPool(){
        JedisPoolConfig config = getRedisConfig();
        JedisPool jedisPool = new JedisPool(config, "11.176.249.147", 6379, 1000, "CRNspftFrCEBJ26v");
        return jedisPool.getResource();
    }

    //@Bean("getTestJedisTemplte")
    public RedisTemplate<?, ?> getTestJedisTemplte(){
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration("my-master", Sets.newHashSet("redis-sentinel-3.b80ea58f934f4ee9a53c57efb5c61065.addon-redis.v1.runtimes.marathon.l4lb.thisdcos.directory:26379,redis-sentinel-1.b80ea58f934f4ee9a53c57efb5c61065.addon-redis.v1.runtimes.marathon.l4lb.thisdcos.directory:26379,redis-sentinel-2.b80ea58f934f4ee9a53c57efb5c61065.addon-redis.v1.runtimes.marathon.l4lb.thisdcos.directory:26379".split(",")));
        JedisPoolConfig config = getRedisConfig();
        JedisConnectionFactory factory = new JedisConnectionFactory(sentinelConfiguration, config);
        factory.setPassword("OMz9kGL6o55BSpsF");
        RedisTemplate<?,?> testJedisTemplte = new StringRedisTemplate(factory);
        return testJedisTemplte;
    }


}
