package com.example.buxiaohui.bxhapp;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class ThreadPrintTest {

    @Test
    public void main() {
        CommonLock commonLock = new CommonLock();
        Thread threada = new Thread(new PrintThread("a", commonLock, "b"));
        Thread threadb = new Thread(new PrintThread("b", commonLock, "c"));
        Thread threadc = new Thread(new PrintThread("c", commonLock, "a"));
        threada.start();
        threadb.start();
        threadc.start();
    }
}


class PrintThread implements Runnable {
    private String name;//线程名称
    private CommonLock commonLock;//公共锁
    private String next;//下一个线程

    public PrintThread(String name, CommonLock commonLock, String next) {
        this.name = name;
        this.commonLock = commonLock;
        this.next = next;
    }

    @Override
    public void run() {
        while (true) {
            synchronized(commonLock) {
                if (this.name.equals(commonLock.activeName)) {
                    int count = commonLock.count.incrementAndGet();
                    if (count > 96) {
                        commonLock.activeName = next;
                        commonLock.notifyAll();
                        break;
                    } else {
                        System.out.println("name=" + name + " count=" + count);
                        commonLock.activeName = next;
                        commonLock.notifyAll();
                    }
                } else {
                    try {
                        commonLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class CommonLock {
    public AtomicInteger count = new AtomicInteger(0);//default
    public String activeName = "a";//默认为a
}