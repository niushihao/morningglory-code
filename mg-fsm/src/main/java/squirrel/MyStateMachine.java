package squirrel;

import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

/**
 * @author qianniu
 * @date 2020/12/25 3:27 下午
 * @desc
 */
public class MyStateMachine extends AbstractStateMachine<MyStateMachine,MyState,MyEvent,MyContext> {
    public void fun1(MyState from, MyState to, MyEvent event, MyContext context) {

        System.out.println("fun1() 方法执行了。。。。。。。。。。。。。 from：" + from + ", to:" + to +", event:" +event +", context:" + context.num );
    }

    public void fun2(MyState from, MyState to, MyEvent event, MyContext context) {

        System.out.println("fun2() 方法执行了。。。。。。。。。。。。。 from：" + from + ", to:" + to +", event:" +event +", context:" + context.num );
    }
}
