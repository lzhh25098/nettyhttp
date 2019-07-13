package cn.xxx.nettyhttp.service;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

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
    public static void main(String[] args) {

    }
}
