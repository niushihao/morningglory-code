package codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 09:37
 * @Desc: byte to pojo解码器，还有一种是pojo to pojo的解码器(MessageToMessageDecoder<I>,泛型为入站的对象类型)
 */
@Slf4j
public class Byte2IntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes() >= 4){
            int i = in.readInt();
            log.info("解码出一个整数:{}",i);
            out.add(i);
        }
    }
}
