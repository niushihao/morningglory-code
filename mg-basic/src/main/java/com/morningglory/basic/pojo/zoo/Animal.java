package com.morningglory.basic.pojo.zoo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 11:41
 * @Desc:   动物
 */
@ExcelTarget("animal")
public class Animal {

    @Excel(name = "动物名称")
    private String name;

    @Excel(name = "动物性别")
    private String sex;

    @Excel(name = "动物年龄")
    private int age;

    @Excel(name = "动物test",mergeVertical =true,mergeRely = {0},fixedIndex =0)
    private String test;

    //@ExcelCollection(name = "动物特性")
    private List<Feature> featureList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public List<Feature> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<Feature> featureList) {
        this.featureList = featureList;
    }
}
