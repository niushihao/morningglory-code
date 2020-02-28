package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 11:56
 * @Desc:
 */
@Slf4j
public class InHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void handlerAdded(ChannelHandlerContext context) throws Exception{
        log.info("handler被加入pipeline时被调用:handlerAdded()");
        super.handlerAdded(context);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext context) throws Exception{
        log.info("通道成功绑定一个NioEventLoop线程后被调用:channelRegistered()");
        super.channelRegistered(context);
    }

    @Override
    public void channelActive(ChannelHandlerContext context) throws Exception{
        log.info("通过被激活成功后被调用:channelActive()");
        super.channelActive(context);
    }

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg) throws Exception{
        log.info("有数据进站时被调用:channelRead()");
        super.channelRead(context,msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) throws Exception{
        log.info("流水线处理完时被调用:channelReadComplete()");
        super.channelReadComplete(context);
    }

    @Override
    public void channelInactive(ChannelHandlerContext context) throws Exception{
        log.info("通道底层链接已关闭时被调用:channelInactive()");
        super.channelInactive(context);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext context) throws Exception{
        log.info("通道和NioEventLoop线程解除绑定时被调用:channelUnregistered()");
        super.channelUnregistered(context);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext context) throws Exception{
        log.info("移除通道上的处理器时被调用:channelRemoved()");
        super.handlerRemoved(context);
    }


}
