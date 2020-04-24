package cn.zlfjw.channelhandlerandcodec;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpPipelineInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(client){
            //客户端对响应进行解码
            pipeline.addLast("decoder",new HttpResponseDecoder());
            //客户端对请求进行编码
            pipeline.addLast("encoder",new HttpRequestEncoder());
        }else{
            //服务端对请求解码
            pipeline.addLast("decoder",new HttpRequestDecoder());
            //服务端对响应编码
            pipeline.addLast("encoder",new HttpResponseEncoder());
        }
    }
}
