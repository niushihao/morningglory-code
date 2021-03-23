package com.morningglory.basic.desensitizer;

/**
 * @author qianniu
 * @date 2020/7/30 9:09 下午
 * @desc
 */
public interface Desensitizer {

    /**
     * 数据脱敏
     * @param message
     * @return
     */
    String desensitize(String message);

}
