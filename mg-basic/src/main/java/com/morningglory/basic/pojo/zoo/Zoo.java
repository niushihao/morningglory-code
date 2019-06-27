package com.morningglory.basic.pojo.zoo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-03-14 11:43
 * @Desc:   动物园
 */

public class Zoo {

    @Excel(name = "动物园名称",needMerge = true)
    private String name;

    @Excel(name = "动物园地址",needMerge = true)
    private String address;

    private String test;

    @ExcelCollection(name = "动物们")
    private List<Animal> animals;

    //@ExcelCollection(name = "动物们")
    private List<String> animalList= Lists.newArrayList("动物1","动物2","动物3");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<String> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<String> animalList) {
        this.animalList = animalList;
    }
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
