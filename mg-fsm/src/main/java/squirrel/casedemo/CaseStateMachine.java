package squirrel.casedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.annotation.*;
import org.squirrelframework.foundation.fsm.impl.AbstractUntypedStateMachine;

/**
 * @author qianniu
 * @date 2020/12/29 1:58 下午
 * @desc
 */
@Slf4j
@States({
        @State(initialState= true,name = "DRAFT",entryCallMethod="entryStateA", exitCallMethod="exitStateA"),
        @State(name = "UNREVIEWED"),
        @State(name = "RETURNED"),
        @State(name = "TOBEMODIFIED"),
        @State(name = "WAREHOUSING"),
        @State(name = "PENDING"),
        @State(name = "NOTRECOMMENDED"),
        @State(name = "CLOSED")
})
@Transitions({
        @Transit(from = "*",to = "DRAFT",on = "SAVE",callMethod = "change"),
        @Transit(from = "PENDING",to = "NOTRECOMMENDED",on = "NOTRECOMMENDED",callMethod = "change"),
        @Transit(from = "DRAFT",to = "UNREVIEWED",on = "SUBMIT",callMethod = "change"),
        @Transit(from = "UNREVIEWED",to = "RETURNED",on = "REJECT",callMethod = "change"),
        @Transit(from = "UNREVIEWED",to = "TOBEMODIFIED",on = "TOBEMODIFIED",callMethod = "change"),
        @Transit(from = "UNREVIEWED",to = "WAREHOUSING",on = "WAREHOUSING",callMethod = "change"),
        @Transit(from = "WAREHOUSING",to = "PENDING",on = "DESIGN",callMethod = "change"),

        @Transit(from = "PENDING",to = "CLOSED",on = "CASECLOSED",callMethod = "change")
})
@StateMachineParameters(stateType=CaseStatus.class, eventType=CaseEvent.class, contextType=CaseContext.class)
public class CaseStateMachine extends AbstractUntypedStateMachine {

    public CaseStateMachine() {
    }

    public void change(CaseStatus from, CaseStatus to, CaseEvent event, CaseContext context){
        log.info("invoke change method from :{},to :{},event :{},context :{}",from,to,event,context);
    }

    public void entryStateA(CaseStatus from, CaseStatus to, CaseEvent event, CaseContext context){
        //log.info("invoke entryStateA method from :{},to :{},event :{},context :{}",from,to,event,context);
    }

    public void exitStateA(CaseStatus from, CaseStatus to, CaseEvent event, CaseContext context){
        //log.info("invoke exitStateA method from :{},to :{},event :{},context :{}",from,to,event,context);
    }

    public void transitFromAnyToDRAFTOnSAVE(CaseStatus from, CaseStatus to, CaseEvent event, CaseContext context){
        log.info("invoke transitFromAnyToDRAFTOnSAVE method from :{},to :{},event :{},context :{}",from,to,event,context);
    }


    private ApplicationContext applicationContext;

    public CaseStateMachine(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}
