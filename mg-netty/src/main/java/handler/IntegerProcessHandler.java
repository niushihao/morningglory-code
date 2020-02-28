package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 09:46
 * @Desc: 得配合解码器使用，否则强转会失败
 * @See: Byte2IntegerDecoder;Byte2IntegerReplayingDecoder;IntegerAddDecoder
 */
@Slf4j
public class IntegerProcessHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext context,Object msg){
        Integer integer = (Integer) msg;
        log.info("打印出一个整数:{}",integer);

    }
}
