package cn.zlfjw.channelhandlerandcodec;

import io.netty.channel.*;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;

//netty支持websocket协议
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(
                new HttpServerCodec(),
                //1.添加 HttpObjectAggregator 用于提供在握手时聚合 HttpRequest
                new HttpObjectAggregator(65536),
                //2.添加 WebSocketServerProtocolHandler 用于处理色好给你寄握手如果请求是发送到"/websocket." 端点，
                // 当升级完成后，它将会处理Ping, Pong 和 Close 帧
                new WebSocketServerProtocolHandler("/websocket"),
                //3.TextFrameHandler 将会处理 TextWebSocketFrames
                new TextFrameHandler(),
                //4.BinaryFrameHandler 将会处理 BinaryWebSocketFrames
                new BinaryFrameHandler(),
                //5.ContinuationFrameHandler 将会处理ContinuationWebSocketFrames
                new ContinuationFrameHandler()
        );
    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // Handle text frame
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
            // Handle binary frame
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        @Override
        public void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            // Handle continuation frame
        }
    }
}
