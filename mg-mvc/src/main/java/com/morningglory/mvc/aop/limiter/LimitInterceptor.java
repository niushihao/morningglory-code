package com.morningglory.mvc.aop.limiter;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * @author qianniu
 * @date 2020/8/17 2:11 下午
 * @desc
 */
@Aspect
@Configuration
@Slf4j
public class LimitInterceptor {

    private static final String SCRIPT =
            "local ratelimit_info = redis.pcall('HMGET',KEYS[1],'last_time','current_token') local last_time = ratelimit_info[1] local current_token = tonumber(ratelimit_info[2]) local max_token = tonumber(ARGV[1]) local token_rate = tonumber(ARGV[2]) local current_time = tonumber(ARGV[3]) local reverse_time = 1000/token_rate if current_token == nil then current_token = max_token last_time = current_time else local past_time = current_time-last_time; local reverse_token = math.floor(past_time/reverse_time) current_token = current_token+reverse_token; last_time = reverse_time*reverse_token+last_time if current_token>max_token then current_token = max_token end end local result = '0' if(current_token>0) then result = '1' current_token = current_token-1 end redis.call('HMSET',KEYS[1],'last_time',last_time,'current_token',current_toke  redis.call('pexpire',KEYS[1],math.ceil(reverse_time*(max_tokencurrent_token)+(current_time-last_time))) return result";


    public LimitInterceptor(){
        log.info("LimitInterceptor init");
    }

    @Resource
    private RedisTemplate<String, Serializable> redisTemplate;

    @Around(value = "@annotation(limit)")
    public Object invokeAspect(ProceedingJoinPoint joinPoint, Limit limit) throws Throwable {

        String name = limit.name();
        String key = limit.key();
        int limitPeriod = limit.period();
        int limitCount = limit.count();

        try {

            String countStr = redisTemplate.execute(new RedisReteLimitScript(), Lists.newArrayList(key), limitCount, limitPeriod);
            log.info("Access try count is {} for name={} and key = {}", limitCount, name, key);
            long count = Long.parseLong(countStr);
            if( count < limitCount){
                return joinPoint.proceed();
            }else {
                throw new RuntimeException("You have been dragged into the blacklist");
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("server exception");
        }

    }

    private String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }
}
