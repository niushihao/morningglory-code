package com.morningglory.basic.lambda.function;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author qianniu
 * @date 2021/1/6 1:32 下午
 * @desc 执行两次查询,第二次命中了缓存
 */
@Slf4j
public class CacheFunction {

    private static Map cache = Maps.newHashMap();
    public static <T,R> R get(Function<T,R> function, T key){
        R value = (R) cache.get(key);
        if(Objects.nonNull(value)){
            log.info("命中缓存");
            return value;
        }

        value = function.apply(key);
        cache.putIfAbsent(key,value);
        return value;
    }

    public static void main(String[] args) {

        CacheFunction.get(key->key+"1","str");
        CacheFunction.get(key->key+"1","str");
    }
}
