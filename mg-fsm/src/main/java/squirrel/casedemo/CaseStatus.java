package squirrel.casedemo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qianniu
 * @date 2020/12/29 1:50 下午
 * @desc
 */
@AllArgsConstructor
@Getter
public enum CaseStatus {

    DRAFT("草稿"),
    UNREVIEWED("待审核"),
    RETURNED("已退回"),
    TOBEMODIFIED("待修改"),
    WAREHOUSING("入库"),
    PENDING("待结案"),
    NOTRECOMMENDED("不推荐"),
    CLOSED("已结案");

    String desc;
}
