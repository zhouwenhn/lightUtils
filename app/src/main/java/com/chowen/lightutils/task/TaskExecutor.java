package com.chowen.lightutils.task;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 * @author zhouwen
 * @version 1.0
 * @since 2016/6/28 11:28
 */
public class TaskExecutor implements Executor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final int KEEP_ALIVE = 1;

    private static final int CAPACITY = 128;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "TaskExecutor #" + mCount.getAndIncrement());
        }
    };

    private static final BlockingQueue<Runnable> sPoolWorkQueue =
            new LinkedBlockingQueue<Runnable>(CAPACITY);

    private static final Executor sThreadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    private static ScheduledThreadPoolExecutor sScheduledThreadPoolExecutor = null;

    private static Handler mMainHandler;

    @Override
    public void execute(Runnable r) {
        sThreadPoolExecutor.execute(r);
    }

    public static ScheduledFuture<?> scheduleExecuteTask(Runnable r, long delayMillis) {
        checkScheduledThreadPoolExecutor();
        return sScheduledThreadPoolExecutor.schedule(r, delayMillis,TimeUnit.MILLISECONDS);
    }

    public static ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long initialDelay, long delayMillis){
        checkScheduledThreadPoolExecutor();
        return sScheduledThreadPoolExecutor.scheduleAtFixedRate(r, initialDelay,
                delayMillis, TimeUnit.MILLISECONDS);
    }

    public static boolean executeRunOnUIExecutorTask(Runnable r) {
        checkHandler();
        return mMainHandler.post(r);
    }

    public static boolean executeScheduleRunOnUIExecutorTask(Runnable r, long delayMillis) {
        checkHandler();
        return mMainHandler.postDelayed(r, delayMillis);
    }

    public static Executor getTaskExecutor(){
        return sThreadPoolExecutor;
    }

    public static void shutdown() {
        if (sScheduledThreadPoolExecutor != null)
            sScheduledThreadPoolExecutor.shutdown();
    }

    private synchronized static void checkHandler() {
        if (mMainHandler == null)
            mMainHandler = new Handler(Looper.getMainLooper());
    }

    private static void checkScheduledThreadPoolExecutor() {
        if (sScheduledThreadPoolExecutor == null)
            sScheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
    }
}
