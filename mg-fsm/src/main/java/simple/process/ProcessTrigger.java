package simple.process;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import simple.exception.ProcessException;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/9/22 9:22 上午
 * @desc
 */
@Component
@Slf4j
public class ProcessTrigger {

    @Autowired
    private ProcessRegistry processRegistry;

    public void fire(String bizCode, String operation,  Object context) {
        List<Node> nodes = this.processRegistry.getProcessNodes(bizCode, operation);
        if (CollectionUtils.isEmpty(nodes)) {
            log.error("find nodes is null, bizCode = {}, operation = {}, context = {}", new Object[]{bizCode, operation, JSON.toJSONString(context)});
            throw new ProcessException(MessageFormat.format("The trade node with bizCode [{0}] and operation [{1}] not exists.", bizCode, operation));
        } else {
            nodes.forEach((node) -> {
                //Profiler.record(node.getNodeCode());
                node.execute(context);
                //Profiler.release();
            });
        }
    }
}
