package cn.xxx.nettyhttp.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;



/**
 * @author lzhh
 * @date 2019/7/13 16:08
 * 类说明:http服务器端
 */
public class HttpServer {
    private static final int PORT = 8080;
    private static final EventLoopGroup loop = new NioEventLoopGroup();//表示使用Nio进行请求
    private static final ServerBootstrap strap = new ServerBootstrap();//服务端启动必备
    private static final boolean SSL = true;//表示是否使用https协议请求

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     *
     */
    public static void main(String[] args) throws Exception{
        final SslContext ctx;
        if (SSL){
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            ctx = SslContextBuilder.forServer(ssc.certificate(),ssc.privateKey()).build();
        }else {
            ctx = null;
        }
        try {
            strap.group(loop);
            strap.channel(NioServerSocketChannel.class);
            strap.childHandler(new ServerHandlerInit(ctx));//设置监听过滤器
            //服务器绑定监听端口
            ChannelFuture f = strap.bind(PORT).sync();
            System.out.println("服务器启动成功！端口:"+ PORT);
            //监听服务器关闭监听
            f.channel().closeFuture().sync();

        }finally {
            loop.shutdownGracefully(); //关闭线程组，释放掉所有资源包括创建的线程

        }


    }
}
