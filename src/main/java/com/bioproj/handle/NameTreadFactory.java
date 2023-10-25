package com.bioproj.handle;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameTreadFactory implements ThreadFactory {

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, "[Thread-" + mThreadNum.getAndIncrement()+"]");
        System.out.println(t.getName() + " has been created");
        return t;
    }
}
