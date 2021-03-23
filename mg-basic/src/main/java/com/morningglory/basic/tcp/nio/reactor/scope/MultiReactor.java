package com.morningglory.basic.tcp.nio.reactor.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author qianniu
 * @date 2020/8/18 3:42 下午
 * @desc
 */
@Slf4j
public class MultiReactor {

    private static final int POOL_SIZE = 3;
    private static Executor selectorPool = Executors.newFixedThreadPool(POOL_SIZE);
    private static Reactor boosReactor;
    private static Reactor[] workReactor = new Reactor[2];
    private int port;
    private static int next = 0;


    public MultiReactor(int port) {
        try {
            this.port = port;
            boosReactor = new Reactor();
            for (int i = 0; i < workReactor.length; i++) {
                workReactor[i] = new Reactor();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        Thread mrThread = new Thread(boosReactor);
        mrThread.setName("mainReactor");
        new Acceptor(boosReactor.getSelector(), port); // 将 ServerSocketChannel 注册到 mainReactor

        selectorPool.execute(mrThread);

        for (int i = 0; i < workReactor.length; i++) {
            Thread srThread = new Thread(workReactor[i]);
            srThread.setName("subReactor-" + i);
            selectorPool.execute(srThread);
        }
    }

    public static Reactor getNextReactor(){
        Reactor reactor = workReactor[next];
        if(++next == workReactor.length) next = 0;
        return reactor;
    }


    public static void main(String[] args) throws IOException {

        new MultiReactor(5555).start();
        log.info("start");
    }

}
