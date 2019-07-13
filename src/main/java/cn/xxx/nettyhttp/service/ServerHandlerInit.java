package cn.xxx.nettyhttp.service;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;

/**
 * @author lzhh
 * @date 2019/7/13 16:26
 * 类说明:处理http请求
 */
public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {
    private final SslContext ctx;
    public ServerHandlerInit(SslContext ctx){
        this.ctx = ctx;
    }



    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //处理http的关键Handler
        if (ctx != null){
            pipeline.addLast(ctx.newHandler(ch.alloc()));
        }
        pipeline.addLast("encoder",new HttpResponseEncoder());//编码
        pipeline.addLast("dncoder",new HttpRequestDecoder());//解码
        pipeline.addLast("aggregator",new HttpObjectAggregator(10*1024*1024));
        pipeline.addLast("compressor",new HttpContentCompressor());
        pipeline.addLast("handler",new BusiHandler());//处理业务逻辑

    }
}
