package com.kongxiang.netty.tutorial.runtime;

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
public class Client {

    private static  class  ClientHolder{
        private static  final Client instance = new Client();
    }
    // 客户端需要声明
    private EventLoopGroup group;
    private Bootstrap b;
    private ChannelFuture cf ;

    private Client(){
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
                        //超时handler（当服务器端与客户端在指定时间以上没有任何进行通信，则会关闭响应的通道，主要为减小服务端资源占用）
                        sc.pipeline().addLast(new ReadTimeoutHandler(5));
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });
    }
    // 单例模式
    public  static  Client getInstance(){
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
        final Client client = Client.getInstance();
        try {
            ChannelFuture cf =client.getChannelFuture();
            Request request = null;
            for (int i = 1; i < 10; i++) {
                // Netty为了解决调用者如何获取异步操作结果的问题
                cf = client.getChannelFuture();
                request = new Request();
                request.setId(" NO => " + i);
                request.setName(" mmmm = " + i);
                request.setRequestMessage("数据请求 " + i);
                // 发送消息自定义编码
                cf.channel().writeAndFlush(request);
                // 休眠4s
                TimeUnit.SECONDS.sleep(6);
            }

//        使用f.channel().closeFuture().sync()方法进行阻塞,等待服务端链路关闭之后main函数才退出。
            cf.channel().closeFuture().sync();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("进入子线程...");
                        ChannelFuture cf = client.getChannelFuture();
                        System.out.println(cf.channel().isActive());
                        System.out.println(cf.channel().isOpen());
                        //再次发送数据
                        Request request = new Request();
                        request.setId("" + 11);
                        request.setName("pro" + 11);
                        request.setRequestMessage("数据信息" + 11);
                        cf.channel().writeAndFlush(request);
                        cf.channel().closeFuture().sync();
                        System.out.println("子线程结束.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println("断开连接,主线程结束..");
        }finally {
            // 优雅退出，释放NIO线程组的资源。
//            client.close();
        }
    }


}
