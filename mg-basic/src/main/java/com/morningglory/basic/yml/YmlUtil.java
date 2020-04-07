package com.morningglory.basic.yml;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.reader.UnicodeReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: qianniu
 * @Date: 2020-03-26 09:47
 * @Desc: 测试yml文件读取
 */
@Slf4j

public class YmlUtil {

    public static Map<String,Object> yamlHandler(InputStream inputStream) throws IOException {

        Map<String, Object> result = new LinkedHashMap<>();

        UnicodeReader reader = new UnicodeReader(inputStream);

        // 单文件处理
        Yaml yaml = new Yaml();
        Object object = yaml.load(reader);

        if(object instanceof Map){
            Map map = (Map) object;
            buildFlattenedMap(result,map,null);
        }

        reader.close();
        return result;
    }

    public static void main(String[] args) throws IOException {
        String path = "application.yml";

        //ClassPathResource resource = new ClassPathResource(path);
        InputStream resourceAsStream = ClassLoader.getSystemResourceAsStream(path);

        Map<String, Object> result = yamlHandler(resourceAsStream);
        result.forEach((k,v) -> {
            log.info("key = {},v = {}",k,v);
        });

    }

    private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        //循环读取原数据
        source.forEach((key, value) -> {
            //如果存在路径进行拼接
            if (StringUtils.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            //数据类型匹配
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                //如果是map,就继续读取
                Map<String, Object> map = (Map)value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                Collection<Object> collection = (Collection)value;
                if (collection.isEmpty()) {
                    result.put(key, "");
                } else {
                    int count = 0;
                    Iterator var7 = collection.iterator();

                    while(var7.hasNext()) {
                        Object object = var7.next();
                        buildFlattenedMap(result, Collections.singletonMap("[" + count++ + "]", object), key);
                    }
                }
            } else {
                result.put(key, value != null ? value : "");
            }
        });
    }
}
