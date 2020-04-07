package com.morningglory.basic.properites;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @Author: qianniu
 * @Date: 2020-03-27 10:21
 * @Desc:
 * properties.load 是以换行符为标识，每一行会根据 【:或者=】 最为分界,前边的是key,后边的是value，放到map中
 * 所以如果存在两行有相同的key 会覆盖之前的key,如果行中没有【:或者=】默认当做key,value 是空
 * 如果key有多个值，可以用逗号分隔，然后自行分隔处理
 *
 * 以下是文件内容:
 * nsh=123;nn=222
 * nsh
 * Lld:"345":
 * Haha:3333
 *
 * 以下是打印内容:
 * {Haha=3333, nsh=, Lld="345":}
 */
@Slf4j
public class PropertiesTest {

    public static void main(String[] args) throws IOException {

        String path = "/Users/nsh/Desktop/test.txt";
        //String path = "/Users/nsh/Desktop/Test.java";

        FileInputStream fileInputStream = new FileInputStream(path);

        Properties properties = new Properties();
        properties.load(fileInputStream);
        log.info(properties.toString());

    }
}
