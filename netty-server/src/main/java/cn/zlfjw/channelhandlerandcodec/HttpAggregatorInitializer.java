package cn.zlfjw.channelhandlerandcodec;

import com.sun.net.httpserver.HttpServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(client){
            //client: 添加 HttpClientCodec
            pipeline.addLast("codec",new HttpClientCodec());
        }else {
            //server: 添加 HttpServerCodec 作为我们是 server 模式时
            pipeline.addLast("codec",new HttpServerCodec());
        }
        //添加 HttpObjectAggregator 到 ChannelPipeline, 使用最大消息值是 512kb
        pipeline.addLast("aggregator",new HttpObjectAggregator(521*10224));
    }
}
