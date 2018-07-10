package com.zhangyujie.repost.env;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class RepostEnv {
    public static BlockingQueue<CharSequence> WEIBOIDS = new LinkedBlockingQueue<CharSequence>();
    public static BlockingQueue<CharSequence> USERIDS = new LinkedBlockingQueue<CharSequence>();
}
