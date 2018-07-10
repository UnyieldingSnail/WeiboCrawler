package com.zhangyujie.comment.rpc;

import com.zhangyujie.comment.env.CommentEnv;
import com.zhangyujie.global.env.GlobalEnv;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import rpc.service.RpcSendIds;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 向关系采集服务器发送用户ID
 */
public class RpcClientRunner implements Runnable {

    public void run() {
        while (true) {
            try {
                List<CharSequence> userIds = new ArrayList<CharSequence>();
                for (int i = 0; i < 100; i++) {
                    CharSequence userId = CommentEnv.USERIDS.take();
                    userIds.add(userId);
                }
                RpcSendIds proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress(GlobalEnv.RELATIONIP, GlobalEnv.RELATIONPORT)));
                proxy.sendIds(userIds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
