package com.morningglory.basic.file;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author qianniu
 * @date 2020/8/26 11:10 上午
 * @desc
 */
@Slf4j
public class PatchTest {

    public static void main(String[] args) throws IOException {

        Path path = Paths.get("/Users/nsh/Downloads/1112");

        boolean notExists = Files.notExists(path);
        log.info("notExists = {}",notExists);
        if(notExists){
            Path file = Files.createFile(path);
            log.info("create new file");
            Path parent = file.getParent();
            log.info("parent = {}",parent.toString());
        }

    }
}
