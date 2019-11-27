package com.morningglory.docker.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: qianniu
 * @Date: 2019-09-30 16:33
 * @Desc:
 */
@RestController
public class DockerComposeController {

    @GetMapping
    public String hello(){


        return toString();
    }
}
