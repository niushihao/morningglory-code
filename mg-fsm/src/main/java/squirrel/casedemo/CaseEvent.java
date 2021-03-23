package squirrel.casedemo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qianniu
 * @date 2020/12/29 1:42 下午
 * @desc
 */
@AllArgsConstructor
@Getter
public enum CaseEvent {
    SAVE("保存"),
    SUBMIT("提交"),
    REJECT("驳回"),
    TOBEMODIFIED("待修改"),
    WAREHOUSING("入库"),
    DESIGN("设计"),
    CASECLOSED("结案"),
    NOTRECOMMENDED("不推荐");

    String desc;

}
