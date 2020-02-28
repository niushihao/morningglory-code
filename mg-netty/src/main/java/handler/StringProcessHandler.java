package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 13:34
 * @Desc:
 */
@Slf4j
public class StringProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg){
        String s = (String) msg;
        log.info("打印出一个字符串:{}",s);
    }
}
