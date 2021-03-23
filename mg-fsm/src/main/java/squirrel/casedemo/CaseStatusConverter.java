package squirrel.casedemo;

import org.squirrelframework.foundation.fsm.Converter;

/**
 * @author qianniu
 * @date 2020/12/29 3:44 下午
 * @desc
 */
public class CaseStatusConverter implements Converter<CaseStatus> {

    @Override
    public String convertToString(CaseStatus obj) {
        return obj.name();
    }

    @Override
    public CaseStatus convertFromString(String name) {
        return CaseStatus.valueOf(name);
    }
}
