package com.morningglory.basic.factoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author: qianniu
 * @Date: 2019-03-29 14:21
 * @Desc:
 */
@Component
public class DemoBeanFactory implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return new DemoBean();
    }

    @Override
    public Class<?> getObjectType() {
        return DemoBean.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
