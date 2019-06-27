package com.morningglory.basic.pojo;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;
import java.util.List;

/**
 * @Author: nsh
 * @Date: 2018/1/25
 * @Description:
 */
public class User extends BasePojo{

    private Integer id;

    private String name;

    @NotEmpty(message = "性别不能为空")
    //@EnumValue(enumClass = UserStatusEnum.class,enumMethod = "isValidName",message = "类型不合法")
    private String sex;

    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;

    private List lists;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List getLists() {
        return lists;
    }

    public void setLists(List lists) {
        this.lists = lists;
    }

    public  User() {
    }

    public User(int id) {
        this.id = id;
    }

    Nsh getNsh(){
        return new Nsh();
    }


    public User(String a) {
        System.out.println("user 构造函数");
    }

     public  class Nsh{
        public Nsh() {
            System.out.println("nsh构造函数执行");
            System.out.println("name ="+123);
        }

        public String getNshSex1(){
            return "nsh";
        }
    }

    public String sayAA(){
        return "AA";
    }

    String sayBB(){
        return "BB";
    }

    public static void main(String[] args) {
        User u = new User(){
            @Override
            public String sayAA() {
                return "sub AA";
            }

            @Override
            String sayBB() {
                return "sub BB";
            }
        };
        System.out.println(u.sayAA());
        System.out.println(u.sayBB());
    }
}