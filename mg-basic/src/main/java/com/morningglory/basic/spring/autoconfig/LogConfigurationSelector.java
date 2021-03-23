package com.morningglory.basic.spring.autoconfig;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;

/**
 * @author qianniu
 * @date 2020/6/24 2:46 下午
 * @desc
 */
public class LogConfigurationSelector extends AdviceModeImportSelector<EnableLog> {
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return new String[]{ProxyLogConfiguration.class.getName()};
            case ASPECTJ:
                return new String[]{ProxyLogConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
