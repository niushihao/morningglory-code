package com.morningglory.basic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;

/**
 * @Author: nsh
 * @Date: 2018/8/28
 * @Description:
 */
public class EchoServer {

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    private void start() throws InterruptedException {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress("127.0.0.1",port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch){
                            ch.pipeline()
                                    // 防止粘包.目前发现他的作用是能过滤掉我们在命令行敲的回车
                                    .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(serverHandler);
                        }
                    });

            ChannelFuture f = server.bind().sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boosGroup.shutdownGracefully().sync();
            workerGroup.shutdownGracefully().sync();
        }



    }

    public class EchoServerHandler extends ChannelInboundHandlerAdapter{

        @Override
        public void channelRead(ChannelHandlerContext context,Object msg){
            String in = (String) msg;
            String out ="";
            boolean close = false;

            if(StringUtils.isEmpty(in)){
                out = "Please type something.\nnsh>";
            }else if("bye".equals(in)){
                out = "Have a good day!\n";
                close = true;
            }else {
                out = "Did you say '" + in + "'?\nnsh>";
            }
            System.out.println("server received :"+in);

            //context.write(out);
            ChannelFuture channelFuture = context.write(out);

            if(close){
                channelFuture.addListener(ChannelFutureListener.CLOSE);
            }
        }



        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            System.out.println("ReadComplete");
            //ctx.writeAndFlush(Unpooled.EMPTY_BUFFER) .addListener(ChannelFutureListener.CLOSE);
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx,
                                    Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        int port = 9999;
        new EchoServer(port).start();
    }
}