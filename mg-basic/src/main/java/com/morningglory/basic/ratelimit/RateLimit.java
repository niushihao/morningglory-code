package com.morningglory.basic.ratelimit;

/**
 * @author qianniu
 * @date 2020/11/25 9:38 上午
 * @desc
 */
public interface RateLimit {

    /**
     * can pass
     */
    boolean canPass() throws BlockException;
}
