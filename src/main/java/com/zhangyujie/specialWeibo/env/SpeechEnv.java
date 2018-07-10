package com.zhangyujie.specialWeibo.env;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SpeechEnv {
    public static BlockingQueue<CharSequence> WEIBOIDS = new LinkedBlockingQueue<CharSequence>();
    public static BlockingQueue<CharSequence> USERIDS = new LinkedBlockingQueue<CharSequence>();
}
