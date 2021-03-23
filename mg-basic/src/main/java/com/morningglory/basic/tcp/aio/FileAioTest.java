package com.morningglory.basic.tcp.aio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author qianniu
 * @date 2020/8/26 11:27 上午
 * @desc
 */
@Slf4j
public class FileAioTest {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        Path path = Paths.get("/Users/nsh/Downloads/2ef5fbbde7f52c1acb368f0a40a5903f3d07210d6f7e1160c7717d69c9b1ef3f_stdout.dms");

        aioReturnFuture(path);

        aioRunWithCallBack(path);

    }

    private static void aioRunWithCallBack(Path path) throws IOException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
        channel.read(ByteBuffer.allocate(100_000), 0, null, new CompletionHandler<Integer, ByteBuffer>() {
            public void completed(Integer result, ByteBuffer attachment) {
                log.info("completed = {}",result);
            }

            public void failed(Throwable exc, ByteBuffer attachment) {
                log.info("failed = {}",exc.getCause());
            }
        });

        log.info("主线程继续做事情");
    }

    private static void aioReturnFuture(Path path) throws IOException, ExecutionException, InterruptedException {
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(path);
        Future<Integer> result = channel.read(ByteBuffer.allocate(100_000), 0);

        while (!result.isDone()) {
            log.info("主线程继续做事情");
        }

        Integer bytesRead = result.get();
        log.info("bytesRead = {}",bytesRead);
    }


}
