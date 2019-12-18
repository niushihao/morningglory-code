package com.morningglory.mvc.canal;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.morningglory.mvc.util.CanalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: qianniu
 * @Date: 2019-12-01 13:10
 * @Desc:
 */
@Component
@Slf4j
public class CanalListener {

    private Map<String,CanalHandler> handlerMap;

    @Resource
    private List<CanalHandler> handlerList;

    public void onMessage(Message message){
        List<CanalEntry.Entry> entries = message.getEntries();
        if(CollectionUtils.isEmpty(entries)){
            return;
        }

        dispatch(entries);
    }

    private void dispatch(List<CanalEntry.Entry> entries) {
        for(CanalEntry.Entry entry : entries){
            if(CanalEntry.EntryType.TRANSACTIONBEGIN == entry.getEntryType()
                    || CanalEntry.EntryType.TRANSACTIONEND == entry.getEntryType()){
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

    public void init() {
        if(CollectionUtils.isEmpty(handlerList)){
            return;
        }
        handlerMap = handlerList.stream()
                .collect(Collectors.toMap(CanalHandler::getTable, Function.identity()));
        log.info("canal listener init");
    }
}
