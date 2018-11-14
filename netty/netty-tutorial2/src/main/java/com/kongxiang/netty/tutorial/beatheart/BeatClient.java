package com.kongxiang.netty.tutorial.beatheart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @description:
 *     客户端
 * @projectName: com.kongxiang.netty.tutorial.runtime
 * @className: netty-tutorial2
 * @author:谭农春
 * @createTime:2018/11/14 20:53
 */
public class  BeatClient {

    private static  class  ClientHolder{
        private static  final BeatClient instance = new BeatClient();
    }
    // 客户端需要声明
    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf ;

    private BeatClient(){
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        // 绑定工作组与线程组
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        // 编码解码
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        sc.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
    }
    // 单例模式
    public  static BeatClient getInstance(){
        return  ClientHolder.instance;
    }

    public void close(){
        group.shutdownGracefully();
    }
    /**
     * 创建连接器
     */
    public  void connect(){
        try {
            this.cf = b.connect("127.0.0.1", 8765).sync();
            System.out.println("远程服务器已经连接, 可以进行数据交换..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建通道
     * @return
     */
    public  ChannelFuture getChannelFuture(){
        // 没有通道
        if(this.cf == null){
            this.connect();
        }
        // 通道关闭
        if(!this.cf.channel().isActive()){
            this.connect();
        }
        return this.cf;
    }

    /**
     *  主函数
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        final BeatClient client = BeatClient.getInstance();
        try {
            ChannelFuture cf =client.getChannelFuture();
            // 发送消息
            cf.channel().writeAndFlush("ping");
//        使用f.channel().closeFuture().sync()方法进行阻塞,等待服务端链路关闭之后main函数才退出。
            cf.channel().closeFuture().sync();

            System.out.println("断开连接,主线程结束..");
        }finally {
            // 优雅退出，释放NIO线程组的资源。
//            client.close();
        }
    }


}
