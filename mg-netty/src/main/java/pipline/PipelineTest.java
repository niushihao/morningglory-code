package pipline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 14:45
 * @Desc:
 * 入站的消息按照业务handler的放入顺序执行,如果任意一个处理器没有回调父类的方法，流水线也会被中断
 * 出站的消息按照业务handler的放入逆序顺序执行,如果任意一个处理器没有回调父类的方法，流水线也会中断
 */
public class PipelineTest {

    public static void main(String[] args) {
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline()
                        .addLast(new SimpleOutHandlerGrop.A())
                        .addLast(new SimpleOutHandlerGrop.B())
                        .addLast(new SimpleOutHandlerGrop.C())
                        .addLast(new SimpleInHandlerGroup.A())
                        .addLast(new SimpleInHandlerGroup.B())
                        .addLast(new SimpleInHandlerGroup.C());
            }
        };

        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);

        // 向通道写一个入站报文
        channel.writeInbound(buffer);

        // 向通道写一个出站报文
        channel.writeOutbound(buffer);

    }
}
