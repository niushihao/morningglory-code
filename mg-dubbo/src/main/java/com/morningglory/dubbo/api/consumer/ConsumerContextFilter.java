package com.morningglory.dubbo.api.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author: qianniu
 * @Date: 2020-03-13 17:36
 * @Desc:
 */
@Slf4j
@Activate(group = CommonConstants.CONSUMER)
public class ConsumerContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment("name","nsh");
        Object value = RpcContext.getContext().get("key");
        log.info("consumer[{}] RPC context = {},key = {}",RpcContext.getContext().getMethodName()
                ,RpcContext.getContext().getAttachments(),value);
        return invoker.invoke(invocation);
    }
}
