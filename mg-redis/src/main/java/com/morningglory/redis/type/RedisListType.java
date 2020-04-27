package com.morningglory.redis.type;

import com.morningglory.redis.JedisClient;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-04-09 10:13
 * @Desc:
 * https://www.cnblogs.com/Laymen/p/6121297.html
 */
@Slf4j
public class RedisListType {

    private final static String NAME_SPACE ="redis:list:";
    private final static String DEFAULT_VALUE = "TEST_LIST";
    private static final int TIME_OUT = 10;
    public static void main(String[] args) {

        Jedis jedis = JedisClient.getClientWithDefaultAuth();

        // 从头部添加 将值依次放入list的表头,下例中 [345] 将在表头位置
        Long lpush = jedis.lpush(NAME_SPACE + "lpush", "123", "234", "345");
        log.info("lpush = {}",lpush);

        // 移除并返回列表的头元素
        String lpop = jedis.lpop(NAME_SPACE + "lpush");
        log.info("lpop = {}",lpop);

        // 同 lpop 是它的阻塞版本,如果列表为空，则会一直阻塞
        List<String> blpop = jedis.blpop(TIME_OUT, NAME_SPACE + "lpush");
        log.info("blpop = {}",blpop);

        // 从底部添加 将值依次放入list的尾部,下例中 [123] 将在表头位置
        Long rpush = jedis.rpush(NAME_SPACE + "rpush", "123", "234", "345");
        log.info("rpush = {}",rpush);

        // 移除并返回列表的尾元素
        String rpop = jedis.rpop(NAME_SPACE + "rpush");
        log.info("rpop = {}",rpop);

        // 同 rpop 是它的阻塞版本,如果列表为空，则会一直阻塞
        List<String> brpop = jedis.blpop(TIME_OUT, NAME_SPACE + "rpush");
        log.info("brpop = {}",brpop);

        //**************上边两部分其实一样,只是一个是从列表头部开始操作(left),另一个是从列表底部开始操作(right)


        // 返回列表指定下标的元素(0->第一个元素,1->第二个,-1->最后一个,-2->倒数第二个 。。。)
        String lindex = jedis.lindex(NAME_SPACE + "lpush", 0);
        log.info("lindex = {}",lindex);

        // 向指定下标处添加元素(当index超出范围,或者列表不存在 则报错)
        String lset = jedis.lset(NAME_SPACE + "lpush", 0, "1111");
        log.info("lset = {}",lset);

        // 向 [key] 的列表中 在 [pivot]元素的 [前/后] 添加 [value]
        Long linsert = jedis.linsert(NAME_SPACE + "lpush", BinaryClient.LIST_POSITION.AFTER, "1111", "1111-after");
        log.info("linsert = {}",linsert);

        // 获取指定区间的数据
        List<String> lrange = jedis.lrange(NAME_SPACE + "lpush", 0, -1);
        log.info("lrange = {}",lrange);
    }
}
