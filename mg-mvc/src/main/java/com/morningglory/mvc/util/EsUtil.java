package com.morningglory.mvc.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Author: qianniu
 * @Date: 2019-11-28 20:21
 * @Desc:
 */
public class EsUtil {

    /**
     * SearchHits TO Pojo
     * @param clazz
     * @param hits
     * @param <T>
     * @return
     */
    public static <T> List<T> toPojo(Class<T> clazz, SearchHits hits) throws InvocationTargetException, IllegalAccessException {
        List<T> result = Lists.newArrayListWithCapacity(Long.valueOf(hits.totalHits).intValue());
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit hit = iterator.next();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            T entity = JSON.parseObject(JSON.toJSONString(sourceAsMap), clazz);
            result.add(entity);
        }
        return result;
    }
}
