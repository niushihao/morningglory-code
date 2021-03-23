package com.morningglory.design.template;

/**
 * @author qianniu
 * @date 2020/9/28 1:14 下午
 * @desc
 */
public abstract class CarModel {

    // 发动车辆
    protected abstract void start();

    // 停止车辆
    protected  abstract  void stop();

    // 鸣笛
    protected abstract void alarm();

    // 发动机响
    protected abstract void engineBoom();

    protected boolean isAlarm(){
        return false;
    }

    // 模板方法名
    final public void run(){
        // 先发动
        this.start();

        // 引擎开始轰鸣
        this.engineBoom();

        // 判断是否要鸣笛
        if(isAlarm()){
            this.alarm();
        }

        // 到达目的地
        this.stop();
    }
}
