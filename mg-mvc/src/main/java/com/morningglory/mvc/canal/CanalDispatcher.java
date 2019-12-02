package com.morningglory.mvc.canal;
import com.alibaba.otter.canal.client.CanalConnector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-12-01 13:10
 * @Desc:
 */
@Component
@Slf4j
public class CanalDispatcher{

    @Resource
    private CanalConnector canalConnector;

    @Resource
    private List<CanalHandler> canalHandlerList;

    public void init(){
        initHandler();
        log.info("invoke handlers init method");

        initCanal();
        log.info("invoke canal init method");

        initTask();
        log.info("invoke task init method");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("invoke shutdown hook");
        }));
    }


    public void destroy(){

        destroyCanal();
        log.info("invoke canal destroy method");

        destroyHandler();
        log.info("invoke handlers destroy method");
    }


    private void destroyCanal() {
        canalConnector.disconnect();
    }

    private void destroyHandler() {
        for(CanalHandler handler:canalHandlerList){
            handler.destroy();
        }
    }


    private void initTask() {
        new DispatcherTask(canalHandlerList,false,canalConnector);

    }

    private void initCanal() {
        canalConnector.connect();
        canalConnector.subscribe();
        canalConnector.rollback();
    }

    private void initHandler() {
        for(CanalHandler handler:canalHandlerList){
            handler.init();
        }
    }

}
