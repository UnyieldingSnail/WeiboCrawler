package com.zhangyujie.relation.rpc;

import com.zhangyujie.global.env.GlobalEnv;
import com.zhangyujie.relation.env.RelationEnv;
import com.zhangyujie.repost.env.RepostEnv;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.specific.SpecificResponder;
import rpc.service.RpcSendIds;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 接收用户ID
 */
public class RpcServerRunner implements Runnable {

    public void run() {
        NettyServer server = new NettyServer(new SpecificResponder(RpcSendIds.class, new RpcSendIds() {
            public Void sendIds(List<CharSequence> ids) throws AvroRemoteException {
                RelationEnv.USERIDS.addAll(ids);
                System.out.println("[relation端接收到" + ids.size() + "用户ID]");
                return null;
            }
        }), new InetSocketAddress(GlobalEnv.RELATIONPORT));
        server.start();
        System.out.println("[relation端接收用户ID服务启动]");
    }
}
