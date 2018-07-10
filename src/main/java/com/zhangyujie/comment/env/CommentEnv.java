package com.zhangyujie.comment.env;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CommentEnv {
    public static BlockingQueue<CharSequence> WEIBOIDS = new LinkedBlockingQueue<CharSequence>();
    public static BlockingQueue<CharSequence> USERIDS = new LinkedBlockingQueue<CharSequence>();
}
