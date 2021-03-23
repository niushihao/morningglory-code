package squirrel.casedemo;

import org.squirrelframework.foundation.fsm.Converter;

/**
 * @author qianniu
 * @date 2020/12/29 3:45 下午
 * @desc
 */
public class CaseEventConverter implements Converter<CaseEvent> {

    @Override
    public String convertToString(CaseEvent obj) {
        return obj.name();
    }

    @Override
    public CaseEvent convertFromString(String name) {
        return CaseEvent.valueOf(name);
    }
}
