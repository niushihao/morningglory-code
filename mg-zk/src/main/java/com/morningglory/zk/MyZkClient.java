package com.morningglory.zk;

import lombok.extern.slf4j.Slf4j;
import org.I0Itec.zkclient.ZkClient;

/**
 * @Author: qianniu
 * @Date: 2019-09-26 17:12
 * @Desc:
 */
@Slf4j
public class MyZkClient {

    public static void main(String[] args) {
        ZkClient client = new ZkClient("127.0.0.1:2182");
    }
}
