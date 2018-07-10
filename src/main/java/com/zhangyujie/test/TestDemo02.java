package com.zhangyujie.test;

import com.zhangyujie.comment.env.CommentEnv;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.Transceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import rpc.service.RpcSendIds;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class TestDemo02 implements Runnable{

    public void run() {
        try {
            List<CharSequence> ids = new ArrayList<CharSequence>();
            ids.add("12");
            ids.add("10");
            ids.add("11");
            RpcSendIds proxy = SpecificRequestor.getClient(RpcSendIds.class, new NettyTransceiver(new InetSocketAddress("127.0.0.1", 12345)));
            proxy.sendIds(ids);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
