package com.morningglory.io.nio.buffer;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author qianniu
 * @date 2020/10/30 12:30 下午
 * -Xms4m -Xmx4m -Xmn2m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * @desc
 */
public class ZeroCopy {

    public static void main(String[] args) throws IOException {

        /*FileChannel fileChannel = FileChannel.open(Paths.get("/Users/nsh/Downloads/trade_staging.sql"));
        FileChannel fileChannel1 = FileChannel.open(Paths.get("/Users/nsh/Downloads/trade_staging1.sql"), StandardOpenOption.READ,StandardOpenOption.WRITE);

        fileChannel.transferTo(0,fileChannel.size(),fileChannel1);*/

        File file = new File("/Users/nsh/Downloads/trade_staging.sql");
    }
}
