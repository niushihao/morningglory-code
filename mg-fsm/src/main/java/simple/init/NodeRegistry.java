package simple.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import simple.exception.ProcessException;
import simple.process.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qianniu
 * @date 2020/9/21 8:48 下午
 * @desc
 */
@Component
@Slf4j
public class NodeRegistry {

    private Map<String, Node> nodeNameToNodeMap = new HashMap();

    public void register(String nodeName, Node nodeBean) {
        Node existedNode = (Node)this.nodeNameToNodeMap.get(nodeName);
        if (existedNode != null) {
            log.error("node1: {} , node2: {} have the same node name, please check", nodeBean, existedNode);
            throw new ProcessException("Found that two nodes have the same name");
        } else {
            this.nodeNameToNodeMap.put(nodeName, nodeBean);
        }
    }

    public Node getNode(String nodeName) {
        return (Node)this.nodeNameToNodeMap.get(nodeName);
    }

    public void release() {
        this.nodeNameToNodeMap.clear();
    }
}
