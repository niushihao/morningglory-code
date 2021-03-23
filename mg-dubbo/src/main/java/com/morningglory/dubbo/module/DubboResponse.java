package com.morningglory.dubbo.module;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: qianniu
 * @Date: 2020-03-19 19:01
 * @Desc:
 */
@Data
@Accessors(chain = true)
public class DubboResponse implements Serializable {

    private int id;

    private String name;
}
