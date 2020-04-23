package cn.zlfjw.channel;

import org.junit.Test;

public class ChannelPipeline {

    /**
     * 修改 ChannelPipeline,可以在ChannelPipeline中添加或删除ChannelHandler
     */
    @Test
    public void test01(){
        ChannelPipeline pipeline = null; // get reference to pipeline;
        FirstHandler firstHandler = new FirstHandler(); //1
        pipeline.addLast("handler1", firstHandler); //2
        pipeline.addFirst("handler2", new SecondHandler()); //3
        pipeline.addLast("handler3", new ThirdHandler()); //4

        pipeline.remove("handler3"); //5
        pipeline.remove(firstHandler); //6

        pipeline.replace("handler2", "handler4", new ForthHandler()); //6
    }

    private void remove(FirstHandler firstHandler) {

    }

    private void replace(String handler2, String handler4, ForthHandler forthHandler) {

    }

    private void remove(String handler3) {

    }

    private void addFirst(String handler2, SecondHandler secondHandler) {

    }

    private void addLast(String handler1, FirstHandler firstHandler) {

    }
}
