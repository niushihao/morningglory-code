package pipline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 14:45
 * @Desc:
 */
@Slf4j
public class SimpleInHandlerGroup {

    public static class A extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
            // 没有讲过解码器的话，msg一般是Buffer类型的
            if(msg instanceof Integer){
                msg = (Integer)msg + 1;
            }
            log.info("入站处理器A：msg:{}",msg);
            super.channelRead(context,msg);
        }
    }

    public static class B extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
            if(msg instanceof Integer){
                msg = (Integer)msg + 1;
            }
            log.info("入站处理器B：msg:{}",msg);
            super.channelRead(context,msg);
        }
    }

    public static class C extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext context, Object msg) throws Exception{
            if(msg instanceof Integer){
                msg = (Integer)msg + 1;
            }
            log.info("入站处理器C：msg:{}",msg);
            super.channelRead(context,msg);
        }
    }
}
