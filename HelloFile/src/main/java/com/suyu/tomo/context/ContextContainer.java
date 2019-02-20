//package com.suyu.tomo.context;
//
//public class ContextContainer extends Thread
//{
//    private LocalContext localContext;
//
//    public ContextContainer(LocalContext localContext)
//    {
//        this.localContext = localContext;
//    }
//
//    public int getContextInt()
//    {
//        return this.localContext.intGet();
//    }
//
//    @Override
//    public void run()
//    {
//        System.out.println(Thread.currentThread().getName() + ":" + getContextInt());
//    }
//
//}
