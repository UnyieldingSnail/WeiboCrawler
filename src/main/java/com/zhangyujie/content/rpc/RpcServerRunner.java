package com.zhangyujie.content.rpc;

import com.zhangyujie.content.env.ContentEnv;
import com.zhangyujie.global.env.GlobalEnv;
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
//        ContentEnv.WEIBOIDS.add("11");
        NettyServer server = new NettyServer(new SpecificResponder(RpcSendIds.class, new RpcSendIds() {
            public Void sendIds(List<CharSequence> ids) throws AvroRemoteException {
                ContentEnv.WEIBOIDS.addAll(ids);
                return null;
            }
        }), new InetSocketAddress(GlobalEnv.CONTENTPORT));
        server.start();
        System.out.println("[content端接收微博ID服务启动]");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("[content端接收微博ID服务关闭]");
    }
}
