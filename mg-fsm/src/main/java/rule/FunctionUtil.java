package rule;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author qianniu
 * @date 2021/1/6 12:58 下午
 * @desc
 */
public class FunctionUtil {


    public static <T,R>R cacheFunction(Function<T, R> function, T t, Map<T, R> cache){
        R r = cache.get(t);
        if(Objects.nonNull(r)){
            return r;
        }

        R apply = function.apply(t);
        cache.put(t,apply);
        return apply;
    }
}
