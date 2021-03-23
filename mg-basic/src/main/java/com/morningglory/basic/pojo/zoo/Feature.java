package com.morningglory.basic.pojo.zoo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

/**
 * @author qianniu
 * @date 2020/12/10 4:49 下午
 * @desc
 */
@Data
@ExcelTarget("feature")
public class Feature {

    @Excel(name = "特性名称")
    private String name;

    @Excel(name = "特性名称")
    private String desc;
}
