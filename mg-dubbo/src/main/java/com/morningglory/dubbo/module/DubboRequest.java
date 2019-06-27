package com.morningglory.dubbo.module;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Author: qianniu
 * @Date: 2019-05-24 14:01
 * @Desc:
 */
@Data
@Accessors(chain = true)
public class DubboRequest implements Serializable {


    private int id;

    private String name;
}
