package com.morningglory.leetcode;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-04-10 12:19
 * @Desc: 统计网站每秒的在线用户数
 * 前提：登陆退出有日志记录,分别为 loginTime,logoutTime,userId;不用考虑前一天登陆的情况
 * 1.核心是统计出每秒人数的变化量
 * 2.每秒的在线人数 = 基数 + 这一秒前的变化量
 */
@Slf4j
public class ActiveUser {

    public static void main(String[] args) {

        // 按秒建立数据，存放每秒数据变化量
        int[] times = initTimeArray();

        List<LoginLog> list = mockData();
        for(LoginLog loginLog : list){
            int loginTime = loginLog.getLoginTime();
            int logOutTime = loginLog.getLogoutTime();
            times[loginTime]++;
            times[logOutTime]--;
        }


        // 获取指定时间的用户数量
        int second = 3;
        int userCount = getUserCount(second,times);
        log.info("{}s 的用户量为 ={}",second,userCount);
    }

    /**
     * 获取指定时间的用户数量
     * @param second        秒数
     * @param times         每秒的变化量
     * @return
     */
    private static int getUserCount(int second,int[] times){
        if(second == 0){
            return times[second];
        }

        if(second > times.length){
            log.warn("时间超出范围");
            return 0;
        }

        int count = 0;
        for(int i = 0; i<= second; i++){
            int changeNumber = times[i];
            count += changeNumber;
            log.info("第:{} 秒 新增登陆用户数: {},count: {}",i,changeNumber,count);
        }
        return count;
    }
    /**
     *
     */
    private static int[] initTimeArray() {
        int[] times = new int[60 * 60 * 24];
        for(int i =0; i< times.length; i++){
            times[i] = 0;
        }
        return times;
    }


    @Data
    private static class LoginLog{
        /**
         * 登陆时间
         */
        private int loginTime;

        /**
         * 登出时间
         */
        private int logoutTime;

        /**
         * userId
         */
        private int userId;
    }

    /**
     * 初始化在0s登陆的十个用户和20s登陆的十个用户
     * @return
     */
    private static List<LoginLog> mockData() {

        List<LoginLog> loginLogs = Lists.newArrayList();

        // 第一秒新增三个,分别在 3，4，5秒退出
        LoginLog loginLog1 = new LoginLog();
        loginLog1.setUserId(11);
        loginLog1.setLoginTime(1);
        loginLog1.setLogoutTime(3);
        loginLogs.add(loginLog1);

        LoginLog loginLog2 = new LoginLog();
        loginLog2.setUserId(12);
        loginLog2.setLoginTime(1);
        loginLog2.setLogoutTime(4);
        loginLogs.add(loginLog2);

        LoginLog loginLog3 = new LoginLog();
        loginLog3.setUserId(13);
        loginLog3.setLoginTime(1);
        loginLog3.setLogoutTime(5);
        loginLogs.add(loginLog3);

        // 第二秒新增二个,在第10,11秒退出
        LoginLog loginLog4 = new LoginLog();
        loginLog4.setUserId(14);
        loginLog4.setLoginTime(2);
        loginLog4.setLogoutTime(10);
        loginLogs.add(loginLog4);

        LoginLog loginLog5 = new LoginLog();
        loginLog5.setUserId(15);
        loginLog5.setLoginTime(2);
        loginLog5.setLogoutTime(11);
        loginLogs.add(loginLog5);
        return loginLogs;
    }
}
