package simple.init;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import simple.process.Node;
import simple.process.ProcessNode;

/**
 * @author qianniu
 * @date 2020/9/21 8:53 下午
 * @desc
 */
@Component
@Slf4j
public class NodeRegisterBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    private NodeRegistry nodeRegistry;

    public Object postProcessAfterInitialization(Object bean, String s) throws BeansException {
        ProcessNode nodeAnnotation =  AnnotationUtils.findAnnotation(bean.getClass(), ProcessNode.class);
        if (nodeAnnotation != null) {
            if (!ClassUtils.isAssignableValue(Node.class, bean)) {
                log.error("class : {} doesn't implement node interface", bean.getClass());
                throw new BeanCreationException(bean.getClass() + "  doesn't implement node interface");
            }

            String nodeCode = Strings.isNullOrEmpty(nodeAnnotation.code()) ? bean.getClass().getSimpleName() : nodeAnnotation.code();
            this.nodeRegistry.register(nodeCode, (Node)bean);
        }

        return bean;
    }
}
