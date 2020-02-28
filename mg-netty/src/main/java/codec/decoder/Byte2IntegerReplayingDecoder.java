package codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 11:03
 * @Desc:
 */
@Slf4j
public class Byte2IntegerReplayingDecoder extends ReplayingDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        int i = in.readInt();
        log.info("解码出一个整数:{}",i);
        out.add(i);
    }
}
