package simple.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import simple.config.Process;
import simple.exception.ProcessException;
import simple.init.load.ProcessDefinitionLoader;
import simple.process.Node;
import simple.process.ProcessRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author qianniu
 * @date 2020/9/22 9:30 上午
 * @desc
 */
@Component
@Slf4j
public class ProcessNodeAssembler implements CommandLineRunner {

    @Autowired
    private NodeRegistry nodeRegistry;

    @Autowired
    private ProcessRegistry processRegistry;

    @Autowired
    private ProcessDefinitionLoader processDefinitionLoader;

    @Override
    public void run(String... args) throws Exception {
        Collection<Process> definedProcesses = processDefinitionLoader.loadProcessDefinition();
        if (CollectionUtils.isEmpty(definedProcesses)) {
            return;
        }
        List<Node> nodesOfSingleProcess;
        for (Process p : definedProcesses) {
            List<String> nodeNames = p.getNodeList();
            if (CollectionUtils.isEmpty(nodeNames)) {
                continue;
            }
            nodesOfSingleProcess = new ArrayList<>();
            for (String nodeName : nodeNames) {
                Node node = nodeRegistry.getNode(nodeName);
                if (node == null) {
                    log.error("don't find candidate node bean when build process, node name : {}", nodeName);
                    throw new ProcessException("don't find candidate node bean when build process");
                }
                nodesOfSingleProcess.add(node);
            }
            processRegistry.registerProcess(p, nodesOfSingleProcess);
        }

    }
}
