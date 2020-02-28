import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2019-12-31 13:00
 * @Desc: 使用Pipeline 可以批量处理缓存
 * 测试结果：
 * <10条时 循环处理比使用Pipeline 效率更好
 * =10条时 两者耗时基本相同
 * >10条时 Pipeline的效率更好，并且数量越大时两者效率差距越大
 */
@Slf4j
public class JedisPiplineTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379, 400000);
        jedis.auth("123456");
        String result = jedis.get("jedis");
        log.info("result = {}",result);

        testPipline(jedis);

        testLoop(jedis);
    }

    private static void testLoop(Jedis jedis) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        stopWatch.start("jedis loop");
        for(int i = 20; i< 40; i++){
            String s = String.valueOf(i);
            jedis.setex(s,50,s);
        }
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }

    private static void testPipline(Jedis jedis) {
        Pipeline pipelined = jedis.pipelined();
        List<Response<String>> responses = new ArrayList<>();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("jedis pipline");
        for(int i = 0; i < 20; i++){
            String s = String.valueOf(i);
            responses.add(pipelined.setex(s, 50, s));
        }

        pipelined.sync();
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        for(Response<String> response : responses){
            log.info(response.get());
        }
    }
}
