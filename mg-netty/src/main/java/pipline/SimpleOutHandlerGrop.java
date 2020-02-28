package pipline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 16:05
 * @Desc:
 */
@Slf4j
public class SimpleOutHandlerGrop {

    public static class A extends ChannelOutboundHandlerAdapter {

         @Override
         public void write(ChannelHandlerContext context, Object msg, ChannelPromise promise) throws Exception {
             log.info("出站处理器A:被调用");
             super.write(context,msg,promise);
         }
    }

    public static class B extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext context, Object msg, ChannelPromise promise) throws Exception {
            log.info("出站处理器B:被调用");
            super.write(context,msg,promise);
        }
    }

    public static class C extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext context, Object msg, ChannelPromise promise) throws Exception {
            log.info("出站处理器C:被调用");
            super.write(context,msg,promise);
        }
    }
}
