package squirrel.casedemo;

import org.omg.CORBA.Any;
import org.squirrelframework.foundation.fsm.*;

import java.util.Random;

/**
 * @author qianniu
 * @date 2020/12/29 2:56 下午
 * @desc
 * 每一个线程需要持有一个CaseStateMachine对象
 */
public class Launcher {

    public static void main(String[] args) {

        ConverterProvider.INSTANCE.register(CaseEventConverter.class,new CaseEventConverter());
        ConverterProvider.INSTANCE.register(CaseStatusConverter.class,new CaseStatusConverter());
//        StateMachineBuilder<CaseStateMachine, CaseStatus, CaseEvent, CaseContext> builder =
//                StateMachineBuilderFactory.create(CaseStateMachine.class,
//                        CaseStatus.class,
//                        CaseEvent.class,
//                        CaseContext.class);

        UntypedStateMachineBuilder builder = StateMachineBuilderFactory.create(CaseStateMachine.class);
        builder.transit().fromAny().to(CaseStatus.DRAFT).on(CaseEvent.SAVE);

        UntypedStateMachine machine1 = builder.newStateMachine(CaseStatus.DRAFT);
        UntypedStateMachine machine2 = builder.newStateMachine(CaseStatus.DRAFT);

        machine1.start();
        machine2.start();


        Runnable runnable1 = () -> {
            machine1.fire(CaseEvent.SAVE);

            machine1.fire(CaseEvent.SUBMIT,new CaseContext(new Random().toString()));
            Thread.yield();
            machine1.fire(CaseEvent.REJECT);
        };
        new Thread(runnable1,"demo_1").start();

        Runnable runnable2 = () -> {
            machine2.fire(CaseEvent.SUBMIT,new CaseContext(new Random().toString()));
        };


        //new Thread(runnable2,"demo_2").start();
    }
}
