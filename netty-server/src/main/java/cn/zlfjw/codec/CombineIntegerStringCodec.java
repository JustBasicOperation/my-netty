package cn.zlfjw.codec;

import cn.zlfjw.codec.decode.IntegerToStringDecoder;
import cn.zlfjw.codec.encode.IntegerToStringEncoder;
import io.netty.channel.CombinedChannelDuplexHandler;

public class CombineIntegerStringCodec extends CombinedChannelDuplexHandler<IntegerToStringDecoder, IntegerToStringEncoder> {
    public CombineIntegerStringCodec() {
        super(new IntegerToStringDecoder(),new IntegerToStringEncoder());
    }
}
