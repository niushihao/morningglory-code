package com.morningglory.basic.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @Author: nsh
 * @Date: 2018/8/28
 * @Description:
 */
public class EchoClient {

    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline()
                                    .addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture f= b.connect(host,port).sync();
            //等待异步连接操作
            f.channel().closeFuture().sync();

        }catch (Exception e){

        }finally {
            group.shutdownGracefully().sync();
        }
    }



    @ChannelHandler.Sharable
    public class EchoClientHandler extends SimpleChannelInboundHandler{

        /**
         * 当被通知 Channel 是活跃的时候，发 送一条消息
         * @param ctx
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                    CharsetUtil.UTF_8));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("Client received: " + msg.toString());
        }
    }

    public static void main(String[] args) throws Exception {

        String host = "127.0.0.1";
        int port = 9999;
        new EchoClient(host, port).start();
        Thread.sleep(1000000);
    }
}