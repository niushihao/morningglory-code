package com.morningglory.mvc.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.morningglory.mvc.util.CanalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-12-02 16:32
 * @Desc:
 */
@Slf4j
public class DispatcherTask extends Thread{

    private static int DEFAULT_BATCH_SIZE = 5 * 1024;

    private Map<String,CanalHandler> handlerMap;
    private volatile Boolean shutDown = false;
    private CanalConnector canalConnector;

    public DispatcherTask(List<CanalHandler> canalHandlerList, Boolean shutDown, CanalConnector canalConnector) {
        this.handlerMap = canalHandlerList.stream().collect(Collectors.toMap(CanalHandler::getTable, Function.identity()));
        this.shutDown = shutDown;
        this.canalConnector = canalConnector;
    }

    public void shutDown(){
        this.shutDown = true;
    }

    @Override
    public void run() {
        log.info("canal task 开始工作");
        while (!shutDown){
            Message message = canalConnector.getWithoutAck(DEFAULT_BATCH_SIZE);
            if(CollectionUtils.isEmpty(message.getEntries())){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            doDispatcher(message.getEntries());
            canalConnector.ack(message.getId());

        }
        log.info("canal task 停止工作");
    }

    private void doDispatcher(List<CanalEntry.Entry> entries) {

        for(CanalEntry.Entry entry : entries){
            if(CanalEntry.EntryType.TRANSACTIONBEGIN == entry.getEntryType() || CanalEntry.EntryType.TRANSACTIONEND == entry.getEntryType()){
                continue;
            }

            try {
                CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                CanalEntry.EventType rowChangeEventType = rowChange.getEventType();
                if(CanalEntry.EventType.QUERY == rowChangeEventType){
                    continue;
                }

                String schemaName = entry.getHeader().getSchemaName();
                String tableName = entry.getHeader().getTableName();
                List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();

                for(CanalEntry.RowData rowData : rowDataList){
                    CanalHandler handler = this.handlerMap.get(tableName);
                    if(handler == null){
                        log.warn("tableName:{} 没有对应的处理器",tableName);
                        continue;
                    }
                    ParameterizedType parameterized = (ParameterizedType)handler.getClass().getGenericInterfaces()[0];
                    Class pojoClass = (Class) parameterized.getActualTypeArguments()[0];
                    Object entity = null;
                    switch (rowChangeEventType){
                        case INSERT:
                            entity = CanalUtil.toPojo(pojoClass, rowData.getAfterColumnsList());
                            handler.doInsert(entity);
                            break;
                        case DELETE:
                            entity = CanalUtil.toPojo(pojoClass, rowData.getBeforeColumnsList());
                            handler.doDelete(entity);
                            break;
                        case UPDATE:
                            entity = CanalUtil.toPojo(pojoClass, rowData.getAfterColumnsList());
                            handler.doUpdate(entity);
                            break;
                        case CREATE:
                            handler.doCreate(entity);
                            break;
                        case ALTER:
                            handler.doAlter(entity);
                            break;
                    }

                }

            } catch (InvalidProtocolBufferException e) {

            }

        }


    }
}
