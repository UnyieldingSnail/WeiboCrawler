package com.zhangyujie.repost.rpc;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.repost.env.RepostEnv;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;
import rpc.service.RpcSendIds;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 接收微博ID
 */
public class RpcServerRunner implements Runnable {

    public void run() {
        NettyServer server = new NettyServer(new SpecificResponder(RpcSendIds.class, new RpcSendIds() {
            public Void sendIds(List<CharSequence> ids) throws AvroRemoteException {
                RepostEnv.WEIBOIDS.addAll(ids);
                return null;
            }
        }), new InetSocketAddress(GlobalEnv.REPOSTPORT));
        server.start();
        System.out.println("[repost端接收微博ID服务启动]");
    }
}
