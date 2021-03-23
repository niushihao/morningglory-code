package asm.read;

import org.springframework.stereotype.Service;

/**
 * @Author: qianniu
 * @Date: 2020-04-01 12:35
 * @Desc:
 */
@Service("demoClass")
public class DemoClass implements DemoInterface {

    private final int number = 0;

    private String str;

    public String getStr(){
        return this.str;
    }

    public void setStr(String msg){
        this.str = msg;
    }
}
