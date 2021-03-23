package simple.example.node;

import lombok.extern.slf4j.Slf4j;
import simple.process.Node;
import simple.process.ProcessNode;

/**
 * @author qianniu
 * @date 2020/9/22 9:17 上午
 * @desc
 */
@ProcessNode
@Slf4j
public class StudentNode implements Node<DemoContext> {

    @Override
    public void execute(DemoContext demoContext) {
        demoContext.setStudentName("studentName");
        log.info("StudentNode execute");
    }
}
