package com.adam.collection.test.Thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author: Adam Inc.
 * <br/>
 * Created at: 2020/10/22 15:44
 *线程池管理，管理整个项目中所有的线程，所以不能有多个实例对象
 * <br/>
 *
 * @since
 */
public class ThreadPoolManager {
    private  static  ThreadPoolManager mInstance;
    public  static  ThreadPoolManager getInstance(){
        mInstance=new ThreadPoolManager();
        return mInstance;
    }
    private static int corePoolSize;//核心线程池的数量，同时能够执行的线程数量
    private static int maximumPoolSize;//最大线程池数量，表示当缓冲队列满的时候能继续容纳的等待任务的数量
    private static long keepAliveTime=1;//存活时间
    private static TimeUnit unit=TimeUnit.HOURS;
    private ThreadPoolExecutor executor;
    private ThreadPoolManager(){
        /*
        给corePoolSize赋值，当前设备可用处理器核心数*2+1，能够让cpu的效率得到最大程度执行
         */
        corePoolSize=Runtime.getRuntime().availableProcessors()*2+1;
        maximumPoolSize=corePoolSize;//虽然maximumPoolSize用不到，但是需要赋值，否则报错
        executor= (ThreadPoolExecutor) newFixedThreadPool();
    }
    /*
    FixedThreadPool
    线程数量固定，线程空闲不会被回收，除非线程池关闭，
    所有线程处于活动状态时，新任务处于等待状态，知道有线程空闲出来。
    更快速地响应外界请求，没有超时机制，任务队列也是没有大小限制。
     */
    private  static ExecutorService newFixedThreadPool(){
        return new ThreadPoolExecutor(
                corePoolSize,//当某个核心任务执行完毕，会一次从缓冲队列中取出等待任务
                maximumPoolSize,//5,先corePoolSize,然后new LinkedBlockingQueue<Runnable>(),然后maximumPoolSize,但是它的数量是包含了corePoolSize的
                keepAliveTime,//表示的是maximumPoolSize当中等待任务的存活时间
                unit,
                new LinkedBlockingQueue<Runnable>(),//缓冲队列，用于存放等待任务，Linked的先进先出
                Executors.defaultThreadFactory(),//创建线程的工厂
                new ThreadPoolExecutor.AbortPolicy()//用来对超过maximumPoolSize的任务的处理策略

        );
    }
    /*
        CachedThreadPool
        线程数不定，只有非核心线程，最大为Integer.MAX_VALUE,
        当线程都在活动状态时。线程池会创建新的线程来处理新任务，否则就例用空闲的线程
        空闲线程有超时机制 60秒后闲置线程会被回收。
        适合执行大量的耗时较少的任务，整个线程池都闲置时，线程池里面没有任何线程，几乎不占用系统资源
     */
    public  static  ExecutorService newCachedThreadPool(){
        return new ThreadPoolExecutor(0,Integer.MAX_VALUE,
                60L,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
    }
    /*
    ScheduledThreadPool
    核心线程数时固定的 非核心线程数时没有限制的，非核心线程限制时会被立即回收
    适合执行定时任务和具有固定周期的重复任务
     */
    public  static ScheduledExecutorService newScheduledThreadPool(){
        return  new ScheduledThreadPoolExecutor(corePoolSize);
    }
    /*
    SingleThreadExecutor
    只有一个核心线程，确保所有的任务都在同一个线程中按顺序执行，他的意义在于统一所有的外界任务到一个线程中
    这使得这些任务之间不需要处理线程同步的问题
     */
    public static ExecutorService newSingleThreadExecutor() {

        return   Executors.newSingleThreadExecutor();

    }
        /**
         * 执行任务
         */
    public void  execute(Runnable runnable){
        if(runnable==null)return;
        executor.execute(runnable);
    }
    /*
    从线程池中移除任务
     */
    public  void remove(Runnable runnable){
        if(runnable==null)return;
        executor.remove(runnable);
    }
}
