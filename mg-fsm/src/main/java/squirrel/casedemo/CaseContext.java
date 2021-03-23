package squirrel.casedemo;

import lombok.Data;

/**
 * @author qianniu
 * @date 2020/12/29 1:54 下午
 * @desc
 */
@Data

public class CaseContext {

    public CaseContext(String code) {
        this.code = code;
    }

    private String code;

    /**当前状态*/
    private CaseStatus currentStatus;
}
