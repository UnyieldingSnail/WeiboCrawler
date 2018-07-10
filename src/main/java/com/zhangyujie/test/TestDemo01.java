package com.zhangyujie.test;

import com.zhangyujie.comment.env.CommentEnv;
import com.zhangyujie.content.env.ContentEnv;
import com.zhangyujie.global.env.GlobalEnv;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;
import rpc.service.RpcSendIds;

import java.net.InetSocketAddress;
import java.util.List;

public class TestDemo01 implements Runnable {

    public void run() {
        try {
            NettyServer ns = new NettyServer(new SpecificResponder(RpcSendIds.class, new RpcSendIds() {
                public Void sendIds(List<CharSequence> ids) throws AvroRemoteException {
                    ContentEnv.WEIBOIDS.addAll(ids);
                    return null;
                }
            }), new InetSocketAddress(12345));
            ns.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
