package codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 12:49
 * @Desc:
 */
@Slf4j
public class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {

    private int first;
    private int second;

    // 构造函数中初始化父类的status属性

    public IntegerAddDecoder() {
        super(Status.PARSE_1);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state()){
            case PARSE_1:
                // 从装饰器Bytebuf中读取数据
                first = in.readInt();
                // 第一步解析成功后设置 读断点指针
                checkpoint(Status.PARSE_2);
                break;
            case PARSE_2:
                second = in.readInt();
                Integer sum = first + second;
                out.add(sum);
                log.info("解码出了:{}",sum);
                checkpoint(Status.PARSE_1);
                break;
            default:
                break;
        }

    }

    enum Status{
        PARSE_1,PARSE_2
    }
}
