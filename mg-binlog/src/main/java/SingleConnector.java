import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Author: qianniu
 * @Date: 2019-11-27 12:49
 * @Desc:
 */
@Slf4j
public class SingleConnector {

    private static boolean run = false;

    public static void main(String[] args) {
        String ip = AddressUtils.getHostIp();
        log.info("ip = {}",ip);
        CanalConnector connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress("127.0.0.1", 11111)
                ,"example"
                ,"","");

        try {
            connector.connect();
            connector.subscribe();
            connector.rollback();
            run = true;
            log.info("canal连接成功");
            int batchSize = 5 * 1024;
            while (run){
                Message message = connector.getWithoutAck(batchSize);
                if(CollectionUtils.isEmpty(message.getEntries())){
                    TimeUnit.SECONDS.sleep(1);
                    continue;
                }

                CanalLogUtil.printSummary(message, message.getId(), batchSize);
                CanalLogUtil.printEntry(message.getEntries());
                connector.ack(message.getId()); // 提交确认
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            connector.disconnect();
        }
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                log.info("stop");
            }
        });
    }
}
