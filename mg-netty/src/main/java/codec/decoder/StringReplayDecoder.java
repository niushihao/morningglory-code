package codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 13:26
 * @Desc: 字符串数据包解析,因为不是所有的Buf都能被ReplayingDecoderByteBuf装饰,所以正式环境一般不使用这种方式
 */
@Slf4j
public class StringReplayDecoder extends ReplayingDecoder<StringReplayDecoder.Status> {


    private int length;
    private byte[] inBytes;

    public StringReplayDecoder() {
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){
            case PARSE_1:
                // 从装饰器ByteBuf中取长度
                length = in.readInt();
                inBytes = new byte[length];

                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                // 从装饰器ByteBuf中读取内容
                in.readBytes(inBytes,0,length);
                String msg = new String(inBytes, "UTF-8");
                log.info("解码出:{}",msg);
                out.add(msg);
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }
    }

    enum Status{
        PARSE_1,PARSE_2;
    }
}
