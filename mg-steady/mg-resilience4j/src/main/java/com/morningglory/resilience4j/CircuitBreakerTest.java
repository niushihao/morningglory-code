package com.morningglory.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;

@Slf4j
public class CircuitBreakerTest {

    public static Random random = new Random();

    public static void main(String[] args) {


        // Create a custom configuration for a CircuitBreaker
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(1)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .permittedNumberOfCallsInHalfOpenState(2)
                .minimumNumberOfCalls(100)
                .slidingWindowSize(100)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);

        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("test");

        for (int i =0; i < 20; i++) {
            try {
                circuitBreaker.executeSupplier(CircuitBreakerTest::print);
            }catch (Exception e){
                System.out.println("ERROR-------"+e.getMessage());
            }

        }

    }

    public static Boolean print() {
        boolean flag = random.nextBoolean();
        if (!flag) {
            throw new RuntimeException("111");
        }
        return flag;
    }
}
