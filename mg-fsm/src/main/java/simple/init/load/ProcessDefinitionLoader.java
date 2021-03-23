package simple.init.load;

import simple.config.Process;

import java.util.Collection;

/**
 * @author qianniu
 * @date 2020/9/21 8:57 下午
 * @desc
 */
public interface ProcessDefinitionLoader {

    Collection<Process> loadProcessDefinition();
}
