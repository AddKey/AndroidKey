package com.addkey.keylibrary.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 作者:Created by lgy on 2018-03-30.
 * 邮箱:635286598@qq.com
 */

public class ThreadPoolUtils {
    private static ThreadPoolUtils instance;
    /**
     * fixedThreadPool 所有的线程都是核心线程，没有任务可执行的时候也不会被销毁（这样可让FixedThreadPool更快速的响应请求）
     * 线程队列的大小为Integer.MAX_VALUE（2的31次方减1）
     *
     * 工作特点：当所有的核心线程都在执行任务的时候，新的任务只能进入线程队列中进行等待，直到有线程被空闲出来
     *
     */
    private ExecutorService fixedThreadPool;

    /**
     * SingleThreadExecutor的核心线程数只有1
     * 使用SingleThreadExecutor的一个最大好处就是可以避免我们去处理线程同步问题
     */
    private ExecutorService singleThreadExecutor;

    /**
     * CachedTreadPool一个最大的优势是它可以根据程序的运行情况自动来调整线程池中的线程数量，
     * CachedThreadPool中是没有核心线程的，但是它的最大线程数却为Integer.MAX_VALUE，另外，它是有线程超时机制的，超时时间为60秒
     * 根据CachedThreadPool的特点，我们可以在有大量任务请求的时候使用CachedThreadPool，因为当CachedThreadPool中没有新任务的时候，它里边所有的线程都会因为超时而被终止。
     */
    private ExecutorService cachedThreadPool;

    /**
     * ScheduledThreadPool是一个具有定时定期执行任务功能的线程池
     *核心线程数量是固定的（我们在构造的时候传入的），但是非核心线程是无穷大，当非核心线程闲置时，则会被立即回收。
     *
     * 1.延迟启动任务
     * scheduledExecutorService.schedule(runnable, 1, TimeUnit.SECONDS);
     *
     * 2.延迟定时执行任务：延迟initialDelay秒后每个period秒执行一次任务
     * scheduledExecutorService.scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS);
     *
     * 3.延迟执行任务:第一次延迟initialDelay秒，以后每次延迟delay秒执行一个任务
     * scheduledExecutorService.scheduleWithFixedDelay(runnable, 1, 1, TimeUnit.SECONDS);
     */
    private ExecutorService scheduledExecutorService;


    public static ThreadPoolUtils getInstance(){
        if (instance == null){
            synchronized (ThreadPoolUtils.class){
                if (instance == null){
                    instance = new ThreadPoolUtils();
                }
            }
        }
        return instance;
    }
    public ExecutorService getFixedThreadPool(int threadNum){
        if (fixedThreadPool == null){
            fixedThreadPool = Executors.newFixedThreadPool(threadNum);
        }
        return fixedThreadPool;
    }

    public ExecutorService getSingleThreadExecutor(){
        if (singleThreadExecutor == null){
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        return singleThreadExecutor;
    }

    public ExecutorService getCachedThreadPool(){
        if (cachedThreadPool == null){
            cachedThreadPool = Executors.newCachedThreadPool();
        }
        return cachedThreadPool;
    }

    public ExecutorService getScheduledExecutorService(int corePoolSize){
        if (scheduledExecutorService ==null){
            scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
        }
        return scheduledExecutorService;
    }
}
