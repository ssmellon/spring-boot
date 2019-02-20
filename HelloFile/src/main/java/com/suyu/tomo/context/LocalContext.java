package com.suyu.tomo.context;

import java.util.Random;

public class LocalContext implements Runnable {
//    private ThreadLocal<Integer> threadLocal  = new ThreadLocal<>();
    private ThreadLocal<Integer> threadLocal  = new InheritableThreadLocal<>();

    public LocalContext()
    {
        threadLocal.set((int) (Math.random() * 100D));
        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
    }
//
//    public LocalContext loadContext(String id)
//    {
//        this.id = id;
//        this.threadLocal = new ThreadLocal<>();
//        this.threadLocal.set(new Random(23).nextInt());
//        return this;
//    }
//
//    public void intSet(int num)
//    {
//        this.threadLocal.set(num);
//    }
//
//    public int intGet()
//    {
//        return this.threadLocal.get();
//    }
    @Override
    public void run()
    {
        System.out.println(Thread.currentThread().getName() + "   run :" + threadLocal.get());
    }


    public static void main(String[] args)
    {
        Thread t1 = new Thread(new LocalContext(), "A");
        Thread t2 = new Thread(new LocalContext(), "B");
        t1.start();
        t2.start();
    }


}
