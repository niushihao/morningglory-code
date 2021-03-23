package com.morningglory.basic.tcp.nio.reactor.scope;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author qianniu
 * @date 2020/8/18 7:50 下午
 * @desc
 */
@Slf4j
public class Reactor implements Runnable{

    private ConcurrentLinkedQueue<BasicHandler> events = new ConcurrentLinkedQueue<>();
    private Selector selector;

    public Reactor() throws IOException {
        selector = Selector.open();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() { // normally in a new Thread
        try {
            while (!Thread.interrupted()) { // 死循环
                selector.select(); // 阻塞，直到有通道事件就绪
                Set<SelectionKey> selected = selector.selectedKeys(); // 拿到就绪通道 SelectionKey 的集合
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    SelectionKey skTmp = it.next();
                    dispatch(skTmp); // 分发
                }
                selected.clear(); // 清空就绪通道的 key
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment()); // 获取key关联的处理器
        if (r != null) r.run(); // 执行处理
    }

    void reigster(BasicHandler basicHandler) {
        events.offer(basicHandler);
        log.info("注册了");
        selector.wakeup();
    }
}
