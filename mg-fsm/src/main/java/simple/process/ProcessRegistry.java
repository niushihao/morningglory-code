package simple.process;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import simple.config.Process;
import simple.exception.ProcessException;

import java.util.List;
import java.util.Map;

/**
 * @author qianniu
 * @date 2020/9/21 7:59 下午
 * @desc
 */
@Component
@Slf4j
public class ProcessRegistry {

    private Map<Process, List<Node>> processMap = Maps.newHashMap();

    public List<Node> getProcessNodes(String bizCode, String operation) {
        Process process = new Process();
        process.setBizCode(bizCode);
        process.setOperation(operation);
        return (List)this.processMap.get(process);
    }

    public void registerProcess(Process process, List<Node> nodes) {
        List<Node> existedNode = (List)this.processMap.get(process);
        if (existedNode != null) {
            log.error("bizCode : {}, operation: {} expected single matching process definition but found 2, check your process definition file", process.getBizCode(), process.getOperation());
            throw new ProcessException("process definition conflict");
        } else {
            this.processMap.put(process, nodes);
        }
    }
}
