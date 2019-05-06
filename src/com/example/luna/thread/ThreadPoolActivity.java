package com.example.luna.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.luna.base.BaseActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolActivity extends BaseActivity {

    private static final String TAG = "MainActivityTAG";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ExecutorService service = Executors.newFixedThreadPool(2);//包含2个线程对象
        //创建Runnable实例对象
        MyRunnable myRunnable = new MyRunnable();
        //自己创建线程对象的方式
        //Thread t = new Thread(r);
        //t.start(); ---> 调用MyRunnable中的run()
        //从线程池中获取线程对象,然后调用MyRunnable中的run()
        service.submit(myRunnable);
        //再获取个线程对象，调用MyRunnable中的run()
        service.submit(myRunnable);
        service.submit(myRunnable);
        service.submit(myRunnable);
        //注意：submit方法调用结束后，程序并不终止，是因为线程池控制了线程的关闭。将使用完的线程又归还到了线程池中
        //创建Callable实例对象
        MyCallable myCallable = new MyCallable();
        //从线程池中获取线程对象,然后调用MyRunnable中的run()
        service.submit(myCallable);
        //再获取个教练
        service.submit(myCallable);
        service.submit(myCallable);
        //关闭线程池
        service.shutdown();
    }

    public class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            System.out.println("MyCallable");
            System.out.println(Thread.currentThread().getName());
            System.out.println("Callable");
            Log.d(TAG, "MyCallable = " + Thread.currentThread().getName());
            return null;
        }
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            System.out.println("Runnable");
            Log.d(TAG, "Runnable = " + Thread.currentThread().getName());
        }
    }
}