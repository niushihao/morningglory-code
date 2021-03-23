package simple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import simple.example.node.DemoContext;
import simple.process.ProcessTrigger;

/**
 * @author qianniu
 * @date 2020/9/22 9:16 上午
 * @desc
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class);
        ProcessTrigger processTrigger = applicationContext.getBean(ProcessTrigger.class);
        processTrigger.fire("mg_action","action_list",new DemoContext());
    }
}
