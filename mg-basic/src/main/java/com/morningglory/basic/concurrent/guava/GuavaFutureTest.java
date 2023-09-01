package com.morningglory.basic.concurrent.guava;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author qianniu
 * @date 2022/12/16
 * @desc
 */
@Slf4j
public class GuavaFutureTest {

    public static void main(String[] args) {

        ListeningExecutorService listeningExecutorService1 = MoreExecutors.newDirectExecutorService();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

        ListenableFuture<?> submit = listeningExecutorService.submit(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        submit.addListener(() -> log.info("call listener ..."),executorService);

        Futures.addCallback(submit, new FutureCallback<Object>() {

            @Override
            public void onSuccess(@Nullable Object result) {
                log.info("call callback success ...");
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("call callback failure ...");
            }
        },executorService);


        Futures.whenAllComplete(submit,submit);
        Futures.whenAllSucceed(submit).run(null,null);

    }
}
