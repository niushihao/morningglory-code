package com.morningglory.basic.postprocessor;

import org.springframework.beans.BeansException;

/**
 * @Author: qianniu
 * @Date: 2019-04-14 12:04
 * @Desc:
 */
public interface ITestBean {

    Object postProcessBeforeInitialization(Object o, String s) throws BeansException;

    Object postProcessAfterInitialization(Object o, String s);
}
