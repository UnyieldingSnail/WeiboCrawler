package com.zhangyujie.global.env;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GlobalEnv {
    public static final String DATE = "2018-4-15 00:00:00";
    public static final String WEIBOID = "127.0.0.1";
    public static final int WEIBOPORT = 13331;
    public static final String COMMENTIP = "127.0.0.1";
    public static final int COMMENTPORT = 13332;
    public static final String CONTENTIP = "127.0.0.1";
    public static final int CONTENTPORT = 13333;
    public static final String REPOSTIP = "127.0.0.1";
    public static final int REPOSTPORT = 13334;
    public static final String RELATIONIP = "127.0.0.1";
    public static final int RELATIONPORT = 13335;
    public static final ApplicationContext CTX = new ClassPathXmlApplicationContext("conf/spring-*.xml");
    public static Proxy[] PROXY = new Proxy[500];
    public static List<String> USERAGENT = new ArrayList<>();
    static {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(GlobalEnv.class.getResourceAsStream("/proxy/useragent.txt")))) {
            String line = null;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                USERAGENT.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(GlobalEnv.class.getResourceAsStream("/proxy/proxy.txt")))) {
            String line = null;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                String[] arr = line.trim().split(":");
                PROXY[i] = new Proxy(arr[0], Integer.parseInt(arr[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
