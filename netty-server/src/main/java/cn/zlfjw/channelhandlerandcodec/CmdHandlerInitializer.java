package cn.zlfjw.channelhandlerandcodec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;

/**
 * 解码基于分隔符的协议
 */
public class CmdHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                //1.添加一个 CmdDecoder 到管道；将提取 Cmd 对象和转发到在管道中的下一个处理器
                new CmdDecoder(65*1024),
                //2.添加 CmdHandler 将接收和处理 Cmd 对象
                new CmdHandler()
        );
    }

    //3.命令也是 POJO
    public static final class Cmd{
        private final ByteBuf name;
        private final ByteBuf args;

        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }

        public ByteBuf getName() {
            return name;
        }

        public ByteBuf getArgs() {
            return args;
        }
    }

    public static final class CmdDecoder extends LineBasedFrameDecoder {
        public CmdDecoder(int mexLength){
            super(mexLength);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf buffer) throws Exception {
            //4.super.decode() 通过结束分隔从 ByteBuf 提取帧
            ByteBuf frame = (ByteBuf)super.decode(ctx, buffer);
            if(frame == null){
                //5.frame 是空时，则返回 null
                return null;
            }
            //6.找到第一个空字符的索引。首先是它的命令名；接下来是参数的顺序
            int index = frame.indexOf(frame.readerIndex(),frame.writerIndex(),(byte)' ');
            //7.从帧先于索引以及它之后的片段中实例化一个新的 Cmd 对象
            return new Cmd(frame.slice(frame.readerIndex(),index),frame.slice(index+1,frame.writerIndex()));
        }
    }

    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd msg) throws Exception {
            //8.处理通过管道的 Cmd 对象
            // Do something with the command
        }
    }
}
