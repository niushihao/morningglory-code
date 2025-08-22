package com.morningglory.basic.symbolic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author qianniu
 * @date 2020/6/10 10:18 下午
 * @desc 位移运算测试
 * https://blog.csdn.net/xiaochunyong/article/details/7748713
 * >> 1 表示向右移动一位 等同于 除2
 *   0010 -> 0001
 * << 1 表示向左移动一位 等同于 乘2
 *   0010 -> 0100
 */
@Slf4j
public class Displacement {

    private static Long bizMask = 2149580800l;

    public static void main(String[] args) {

        Displacement displacement2 = new Displacement();
        boolean b2 = displacement2.containBizMask(2097152l);
        System.out.println("__________b2++++++++= "+b2);


        Displacement displacement1 = new Displacement();
        boolean b1 = displacement1.containBizMask((long) Math.pow(2, 22));
        log.info("sadfasdfasdfasdf = {}",b1);
        String xxx = "ABC:1";

        String[] split = xxx.split("#");
        String s0 = split[0];
        String s1 = split[0];
        log.info("s0 = {},s1 = {}",s0,s1);
        String str = "拣选完成，后续区域%s，请集货至交接区%s";
        String format = String.format(str, null,null);
        System.out.println(format);

        //demoStart();

        Displacement displacement = new Displacement();
        boolean b = displacement.containBizMask((long) Math.pow(2, 30));
        System.out.println("__________b++++++++= "+b);
        long tag1 = (long) Math.pow(2, 0);
        long tag2 = (long) Math.pow(2, 2);

        displacement.appendBizMask(tag1);
        displacement.containBizMask(tag1);
        displacement.appendBizMask(tag2);
        displacement.removeBizMask(tag2);


    }

    private static void demoStart() {
        log.info("2右移1位 = {}",2 >> 1);
        log.info("2左移1位 = {}",2 << 1);

        int n =16;
        int i = n - (n >> 2);

        log.info("i = {}",i);


        /**
         * 还是老套路，将2个操作数和结果都转换为二进制进行比较：
         * 5转换为二进制：0000 0000 0000 0000 0000 0000 0000 0101
         * 3转换为二进制：0000 0000 0000 0000 0000 0000 0000 0011
         *
         * 第一个操作数的的第n位于第二个操作数的第n位如果都是1，那么结果的第n为也为1，否则为0
         */
        int and = 5 & 3;
        log.info("and num = {}",and);

        /**
         * 5转换为二进制：0000 0000 0000 0000 0000 0000 0000 0101
         * 3转换为二进制：0000 0000 0000 0000 0000 0000 0000 0011
         *
         * 7转换为二进制：0000 0000 0000 0000 0000 0000 0000 0111
         * 位或操作：第一个操作数的的第n位于第二个操作数的第n位 只要有一个是1，那么结果的第n为也为1，否则为0
         *
         */
        int or = 5 | 3;
        log.info("or num = {}",or);


        /**
         *
         * 5转换为二进制：0000 0000 0000 0000 0000 0000 0000 0101
         * 3转换为二进制：0000 0000 0000 0000 0000 0000 0000 0011
         *
         * 6转换为二进制：0000 0000 0000 0000 0000 0000 0000 0110
         * 位异或：第一个操作数的的第n位于第二个操作数的第n位 相反，那么结果的第n为也为1，否则为0
         *
         */
        int temp = 5 ^ 3;
        log.info("temp num = {}",temp);

        /**
         * 5转换为二进制：0000 0000 0000 0000 0000 0000 0000 0101
         * -------------------------------------------------------------------------------------
         * -6转换为二进制：1111 1111 1111 1111 1111 1111 1111 1010
         */
        int no = ~5;
        log.info("no = {}",no);
    }

    /**
     * 添加表
     * @param bizMask
     */
    public void appendBizMask(long bizMask){
        if(null == this.bizMask){
            this.bizMask = 0L;
        }
        this.bizMask |= bizMask;
        log.info("after append this.bizMask =:{}",this.bizMask);
    }

    /**
     * 是否存在某个标
     * @param bizMask
     * @return
     */
    public boolean containBizMask(long bizMask){
        if(null == this.bizMask){
            this.bizMask = 0L;
        }

        if((this.bizMask & bizMask) == bizMask){
            log.info("this.bizMask 包含标:{}",bizMask);
            return true;
        }
        log.info("this.bizMask 不包含标:{}",bizMask);
        return false;
    }

    public void removeBizMask(long bizMask){
        if(null == this.bizMask){
            this.bizMask = 0L;
        }
        // 当this.bizMask中包含这个标的时候 异或运算相当于 减法,当this.bizMask 不包含这个标时 异或相当于加法
        long difference = this.bizMask ^ bizMask;

        // 这个相当于取两个值得最小值,当包含此标时 difference 就是最新的值,不包含时 this.bizMask保持不变
        this.bizMask &= difference;
        log.info("after remove this.bizMask ={},difference = {}",this.bizMask,difference);
    }


}
