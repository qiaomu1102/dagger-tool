package com.springdagger.core.tool.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author: qiaomu
 * @date: 2020/11/10 14:56
 * @Description: TODO
 */
public enum ThreadPools {

    THREAD_POOL;

    private ThreadPoolExecutor threadPoolExecutor;

    private ExecutorService singleThreadExecutor;

    ThreadPools() {
        threadPoolExecutor = new ThreadPoolExecutor(5, 50,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10000),
                new ThreadFactoryBuilder().setNameFormat("Thread-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());

        singleThreadExecutor = Executors.newSingleThreadExecutor();
    }

    public void execute(Runnable runnable) {
        THREAD_POOL.threadPoolExecutor.execute(runnable);
    }

    public <T> T submit(Callable<T> callable) throws ExecutionException, InterruptedException {
        final Future<T> submit = THREAD_POOL.threadPoolExecutor.submit(callable);
        return submit.get();

    }

    public void executeSingle(Runnable runnable) {
        THREAD_POOL.singleThreadExecutor.execute(runnable);
    }

    public <T> T submitSingle(Callable<T> callable) throws ExecutionException, InterruptedException {
        final Future<T> submit = THREAD_POOL.singleThreadExecutor.submit(callable);
        return submit.get();
    }
}
