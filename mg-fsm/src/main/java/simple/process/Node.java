package simple.process;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author qianniu
 * @date 2020/9/21 7:49 下午
 * @desc
 */
public interface Node<T> {

    void execute(T t);

    default String getNodeCode(){
        ProcessNode processNode = AnnotationUtils.findAnnotation(this.getClass(), ProcessNode.class);
        if(Objects.isNull(processNode)){
            return  this.getClass().getSimpleName();
        }
        return StringUtils.isEmpty(processNode.code()) ? this.getClass().getSimpleName() : processNode.code();
    }
}
