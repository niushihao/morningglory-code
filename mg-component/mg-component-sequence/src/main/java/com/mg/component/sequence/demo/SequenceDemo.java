package com.mg.component.sequence.demo;

import java.sql.SQLException;

/**
 * @author qianniu
 * @date 2022/8/15
 * @desc
 *1、对于单节点的数据库,当本地id用完后都是查询数据库最新的值,然后加步长 重新更新到数据库,多应用机器应该也没问题
 * 比如机器A先启动,更新value为 0+步长,B启动会获取id会把最新的值查出来然后+步长 同样写回到数据库，如果机器宕机重启 则内存中没用完的range会丢失,
 * 会重新从数据库去 然后+步长
 *
 * 2、对于多节点数据库是一样的,不用关系应用实例个数,只是步长 = dsCount*step，但是多数据源会变更,所以需要有个自动调整的功能
 * 初始值：initVal = index * step
 * 触发调整的条件：(value % outStep) != (index * innerStep);
 * 调整设置新值:long newValue = (value - value % outStep) + outStep + index * innerStep
 *
 * 看现在的代码发现 调整数据源个数后,会自动把之前数据源的值调整好，但是ds3的初始值要自己手动维护好
 * ds1      ds2         ds1     ds2     ds3
 *  0       1000   ->   0       1000    2000
 *  2000    3000        3000    4000    5000
 *
 *
 *  fetchSql: 目前看来这个判断没啥用（这个判断我看不能自动计算调整的值）,可以简单理解成 newValue = oldValue + 2000
 *      * if((oldValue + 2000) % 2000 != 1000 * index){
 *      *     newValue = (oldValue + 2000) - (oldValue +2000) % 2000 + 2000 + 1000 * index;
 *      * }else {
 *      *     newValue = oldValue + 2000;
 *      * }
 */
public class SequenceDemo {

    public static void main(String[] args) throws SQLException {

//        String config = "{\"mgSeq\":{\"type\":\"db\",\"mode\":\"1\",\"name\":\"itemSeq\"}}";
//        SequenceBuilder builder = new SequenceBuilder().setName("test").setConfig(config).setDataSourceList(Lists.newArrayList());
//
//        Sequence sequence = builder.build();
//
//        long nextVal = sequence.getNextVal();
        System.out.println(2%3);
    }
}
