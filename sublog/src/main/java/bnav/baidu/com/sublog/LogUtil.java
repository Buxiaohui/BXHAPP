package bnav.baidu.com.sublog;

public class LogUtil {
    public final static boolean LOGGABLE = true;

    public static void e(String tag, String content) {
        System.out.println(tag + "-:-" + content);
    }

    public static void i(String tag, String content) {
        System.out.println(tag + "-:-" + content);
        ;
    }
}
