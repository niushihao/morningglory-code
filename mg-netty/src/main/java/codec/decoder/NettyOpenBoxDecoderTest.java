package codec.decoder;

import handler.StringProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 18:54
 * @Desc: netty最常用的一个解码器
 * @see LengthFieldBasedFrameDecoder
 */
@Slf4j
public class NettyOpenBoxDecoderTest {
    public static final int VERSION = 10;
    public static final String content = "最重要的解码器学习!";

    /**
     * Creates a new instance.
     *
     * @param maxFrameLength(数据包最大长度)
     *        the maximum length of the frame.  If the length of the frame is
     *        greater than this value, {@link TooLongFrameException} will be
     *        thrown.
     * @param lengthFieldOffset(长度字段在整个报文所处的位置)
     *        the offset of the length field
     * @param lengthFieldLength(长度字段所占的字节数,eg:int=4/short=2)
     *        the length of the length field
     * @param lengthAdjustment(长度矫正值)
     *        the compensation value to add to the value of the length field
     *        = 内容字段偏移量 - 长度字段偏移量 - 长度字段字节数
     * @param initialBytesToStrip(丢弃的起始字节数)
     *        the number of first bytes to strip out from the decoded frame
     */
    public static LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder1 = new LengthFieldBasedFrameDecoder(         1024,
    0,
    4,
    0,
    4);

    public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {


        LengthFieldBasedFrameDecoder lengthFieldBasedFrameDecoder = new LengthFieldBasedFrameDecoder(         1024,
                0,
                4,
                0,
                4);

        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(lengthFieldBasedFrameDecoder)
                        .addLast(new StringDecoder(Charset.forName("UTF-8")))
                        .addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for(int i=0; i< 100; i++){
            ByteBuf buffer = Unpooled.buffer();
            String s = i + "次发送->" + content;
            log.info("s= {}",s);
            byte[] bytes = s.getBytes(Charset.forName("UTF-8"));

            buffer.writeInt(bytes.length);
            buffer.writeBytes(bytes);

            channel.writeInbound(buffer);
        }

    }
}
