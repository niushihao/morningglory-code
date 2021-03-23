package com.morningglory.basic.lambda.function;

/**
 * @author qianniu
 * @date 2021/1/6 1:05 下午
 * @desc
 */
public class RetryFunction {

    public static void retry(ThrowExceptionRunnable runnable,int times){
        while (true){
            try {
                runnable.run();
                return;
            }catch (Exception e){
                times--;
                if(times < 0){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T> T retry(ThrowExceptionSupplier<T> supplier ,int times){
        while (true){
            try {
                return supplier.get();
            }catch (Exception e){
                times--;
                if(times < 0){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static <T,R> R retry(ThrowExceptionFunction<T,R> function ,T t,int times){
        while (true){
            try {
                return function.apply(t);
            }catch (Exception e){
                times--;
                if(times < 0){
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {

        //retry(() -> System.out.println("123"),3);

        retry(() -> {
                int a = 1;
                int b = 0;
                b = a/b;
            },4);
    }
}
