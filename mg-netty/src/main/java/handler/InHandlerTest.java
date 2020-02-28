package handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;

/**
 * @Author: qianniu
 * @Date: 2020-01-11 12:09
 * @Desc:
 */
public class InHandlerTest {

    public static void main(String[] args) throws InterruptedException {
        InHandler handlerA = new InHandler();

        // 初始化通道
        ChannelInitializer initializer = new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ch.pipeline().addLast(handlerA);
            }
        };

        // 创建嵌入式通道
        EmbeddedChannel channel = new EmbeddedChannel(initializer);

        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        // 模拟入站
        channel.writeInbound(buffer);
        channel.flush();

        // 模拟入站,在写一个数据包
        channel.writeInbound(buffer);
        channel.flush();

        // 关闭通道
        channel.close();

        Thread.sleep(1000000);

    }
}
