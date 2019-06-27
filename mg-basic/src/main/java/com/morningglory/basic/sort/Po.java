package com.morningglory.basic.sort;

/**
 * @Author: nsh
 * @Date: 2018/8/30
 * @Description:
 */
public class Po {

    private Integer code;

    private String date;

    private String monthStr;

    public Po(Integer code, String date, String monthStr) {
        this.code = code;
        this.date = date;
        this.monthStr = monthStr;
    }

    public Integer getCode() {
        return code;
    }

    public Po setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Po setDate(String date) {
        this.date = date;
        return this;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public Po setMonthStr(String monthStr) {
        this.monthStr = monthStr;
        return this;
    }

    @Override
    public String toString() {
        return "Po{" +
                "code=" + code +
                ", date='" + date + '\'' +
                '}';
    }
}