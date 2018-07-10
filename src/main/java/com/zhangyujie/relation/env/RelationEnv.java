package com.zhangyujie.relation.env;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RelationEnv {
    public static BlockingQueue<CharSequence> USERIDS = new LinkedBlockingQueue<CharSequence>();
}
