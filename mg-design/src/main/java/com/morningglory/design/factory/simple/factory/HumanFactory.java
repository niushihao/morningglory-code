package com.morningglory.design.factory.simple.factory;

import com.morningglory.design.factory.simple.product.Human;
import com.morningglory.design.factory.simple.product.WhiteHuman;
import com.morningglory.design.factory.simple.product.YellowHuman;
import com.morningglory.design.factory.simple.product.BlackHuman;

/**
 * @author qianniu
 * @date 2020/2/24 19:53 下午
 * @desc 简单工厂模式,又称静态工厂模式
 * 当然可以不使用静态方法
 */
public class HumanFactory {

    /**
     * 通过传入业务参数创建不同的对象
     * 缺点是工厂类中会有 if/else 或者 switch的代码
     * 如果增加了一些场景,需要修改工厂类的代码
     * 这种方式也不符合开闭原则
     * @param color
     * @return
     */
    public static Human createHuman(String color){
        if("white".equals(color)){
            return new WhiteHuman();
        }else if("black".equals(color)){
            return new BlackHuman();
        }else if("yellow".equals(color)){
            return new YellowHuman();
        }
        return null;
    }

    /**
     * 通过传入需要创建对象的Class类型
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Human> T createHuman(Class<T> clazz){
        T human = null;
        try {
            human = clazz.newInstance();
        }catch (Exception e){

        }
        return human;
    }
}
