package com.morningglory.dubbo.api.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @Author: qianniu
 * @Date: 2020-03-13 17:45
 * @Desc:
 */
@Slf4j
@Activate(group = CommonConstants.PROVIDER)
public class ProviderContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        log.info("provider[{}] RPC context = {}",RpcContext.getContext().getMethodName(),RpcContext.getContext().getObjectAttachments());
        Result result = invoker.invoke(invocation);
        return result;

    }
}
