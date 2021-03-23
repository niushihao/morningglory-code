package squirrel.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.GenericTypeResolver;
import org.squirrelframework.foundation.fsm.*;
import squirrel.casedemo.CaseContext;
import squirrel.casedemo.CaseEvent;

/**
 * @author qianniu
 * @date 2020/12/29 6:58 下午
 * @desc
 */
public class AbstractCaseStateMachineEngine<T extends UntypedStateMachine> implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    protected UntypedStateMachineBuilder untypedStateMachineBuilder = null;
    //protected StateMachineBuilder stateMachineBuilder = null;

    public AbstractCaseStateMachineEngine() {
        //识别泛型参数
        Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass()
                ,AbstractCaseStateMachineEngine.class);

        untypedStateMachineBuilder= StateMachineBuilderFactory.create(genericType,ApplicationContext.class);
//        stateMachineBuilder = StateMachineBuilderFactory.create(CaseStateMachine.class,
//                CaseStatus.class,
//                CaseEvent.class,
//                CaseContext.class);
    }

    public boolean fire(CaseEvent event, CaseContext context){

        T stateMachine = untypedStateMachineBuilder.newUntypedStateMachine(context.getCurrentStatus(),applicationContext);

        //StateMachine stateMachine = stateMachineBuilder.newStateMachine(context.getCurrentStatus(), applicationContext);

        // TODO 事务处理：获取DataSourceTransactionManager 然后设置传播属性,然后手动提交或回滚

        try {
            stateMachine.fire(event,context);
        }catch (Exception e){
            // 回滚
            return false;
        }

        return !stateMachine.isError();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
