package codec.decoder;

import handler.StringProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.Charset;
import java.util.Random;

/**
 * @Author: qianniu
 * @Date: 2020-01-13 13:42
 * @Desc:
 */
@Slf4j
public class Byte2StringDecoderTest {

    static String content = "学习字符串发送;";
    public static void main(String[] args) {
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new StringReplayDecoder())
                        .addLast(new StringProcessHandler());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));
        for(int i =0; i< 100; i++){
            // 1-4之间的随机数
            int random = new Random().nextInt(4)%4+1;
            ByteBuf buffer = Unpooled.buffer();
            buffer.writeInt(bytes.length * random);
            for(int j =0; j < random; j++){
                buffer.writeBytes(bytes);
            }

            channel.writeInbound(buffer);
        }

    }

}
