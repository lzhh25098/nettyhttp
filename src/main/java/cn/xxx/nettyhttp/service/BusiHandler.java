package cn.xxx.nettyhttp.service;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author lzhh
 * @date 2019/7/13 16:44
 * 类说明:业务处理
 */
public class BusiHandler extends ChannelInboundHandlerAdapter {
    /**
     * 发送报文
     * @param ctx 返回
     * @param context 消息
     * @param status 状态
     */
    private void send(ChannelHandlerContext ctx, String context, HttpResponseStatus status){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,status,
                Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String result = "";
        FullHttpRequest request = (FullHttpRequest)msg;
        System.out.println(request.headers());
        try {
            //获得路径
            String path = request.uri();
            System.out.println("-------------------------->path:"+path);
            //获取body
            String body = request.content().toString();
            System.out.println("---------------------------->body:"+ body);
            //获取请求方法
            HttpMethod method = request.method();
            System.out.println("---------------------------->HttpMethod:"+ method);
            if (!"/test".equals(path)){
                if ("/favicon.ico".equals("/favicon.ico")){
                    return;
                }
                result = "非法请求！" + path;
                System.err.println("------------------------>result"+ result);
                send(ctx,result,HttpResponseStatus.BAD_REQUEST);
                return;
            }
            System.out.println("------------------------->收到请求:"+ method);
            //如果是get请求
            if (HttpMethod.GET.equals(method)){
                //业务处理
                System.out.println("--------------->接受到信息："+body);
                //GET请求应答
                result = method+"应答\n"+RespConstant.getNews();
                send(ctx,result,HttpResponseStatus.OK);
                return;
            }
            //如果其他请求
            if (HttpMethod.POST.equals(method)){
                System.out.println("--------------->收到请求:"+method);
                //应答
                result = method+"应答"+RespConstant.getNews();
                send(ctx,result,HttpResponseStatus.OK);
            }


        }catch (Exception e){
            System.err.println("--------------------处理请求失败----------------->"+ e.getMessage());
            e.printStackTrace();
        }finally {
            request.release();
        }

    }

}
