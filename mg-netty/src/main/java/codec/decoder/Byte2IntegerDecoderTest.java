package codec.decoder;

import handler.IntegerProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 09:49
 * @Desc:
 */
public class Byte2IntegerDecoderTest {

    public static void main(String[] args) {

        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new IntegerAddDecoder())
                        .addLast(new IntegerProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        for(int i = 0; i< 100; i++){
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(i);
            channel.writeInbound(buffer);
        }
    }
}
