package squirrel;

import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;

/**
 * @author qianniu
 * @date 2020/12/25 3:28 下午
 * @desc
 */
public class Test {

    public static void main(String[] args) {

        StateMachineBuilder<MyStateMachine, MyState, MyEvent, MyContext> builder
                = StateMachineBuilderFactory.create(MyStateMachine.class, MyState.class, MyEvent.class, MyContext.class);

//        builder.externalTransition().from(MyState.A).to(MyState.B).on(MyEvent.ToB)
//                .whenMvel("Condition:::(context!=null && context.getNum() == 20)").callMethod("fun1");
        builder.externalTransition().from(MyState.B).to(MyState.C).on(MyEvent.ToC);
        builder.transit().fromAny().toAny().on(MyEvent.ToB).callMethod("fun2");

        MyStateMachine machine = builder.newStateMachine(MyState.B);
        machine.start();

        System.out.println("currentState is " + machine.getCurrentState());
        MyContext context = new MyContext();
        context.setNum(20);
        machine.fire(MyEvent.ToB, context);
        System.out.println("currentState is " + machine.getCurrentState());
    }
}
