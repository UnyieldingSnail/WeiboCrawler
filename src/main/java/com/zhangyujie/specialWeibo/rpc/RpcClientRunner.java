package com.zhangyujie.specialWeibo.rpc;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.specialWeibo.env.SpeechEnv;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import rpc.service.RpcSendIds;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 向微博采集，内容采集，评论采集，关系采集服务器发送微博ID
 */
public class RpcClientRunner implements Runnable {

    public void run() {
        System.out.println("发送微博ID");
        while (true) {
            try {
                List<CharSequence> weiboIds = new ArrayList<CharSequence>();
                List<CharSequence> userIds = new ArrayList<CharSequence>();
                for (int i = 0; i < 10; i++) {
                    CharSequence weiboId = SpeechEnv.WEIBOIDS.take();
                    weiboIds.add(weiboId);
                    CharSequence userId = SpeechEnv.USERIDS.poll();
                    if (userId != null) {
                        userIds.add(userId);
                    }
                }
                System.out.println("开始发送数据");
                RpcSendIds proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress(GlobalEnv.CONTENTIP, GlobalEnv.CONTENTPORT)));
                proxy.sendIds(weiboIds);
                proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress(GlobalEnv.COMMENTIP, GlobalEnv.COMMENTPORT)));
                proxy.sendIds(weiboIds);
                proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress(GlobalEnv.REPOSTIP, GlobalEnv.REPOSTPORT)));
                proxy.sendIds(weiboIds);
                proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress(GlobalEnv.RELATIONIP, GlobalEnv.RELATIONPORT)));
                proxy.sendIds(userIds);
                System.out.println("数据发送完毕");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
