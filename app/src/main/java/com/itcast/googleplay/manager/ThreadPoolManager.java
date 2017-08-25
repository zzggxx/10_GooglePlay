package com.itcast.googleplay.manager;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 对下载的线程池进行封装
 * Created by Lenovo on 2016/8/4.
 */
public class ThreadPoolManager {
    private static ThreadPoolManager mInstance = new ThreadPoolManager();
    private ThreadPoolExecutor executor;

    private final int corePoolSize;
    private final int maximumPoolSize;
    private long keepAliveTime = 1;
    private TimeUnit unit = TimeUnit.HOURS;

    public static ThreadPoolManager getInstance() {
        return mInstance;
    }

    private ThreadPoolManager() {
        corePoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        maximumPoolSize = corePoolSize;
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingDeque<Runnable>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    /**
     * 往线程池中添加任务
     *
     * @param r
     */
    public void execute(Runnable r) {
        if (r != null) {
            executor.execute(r);
        }
    }

    /**
     * 从线程池中移除任务
     *
     * @param r
     */
    public void remove(Runnable r) {
        if (r != null) {
            executor.remove(r);
        }
    }

}
