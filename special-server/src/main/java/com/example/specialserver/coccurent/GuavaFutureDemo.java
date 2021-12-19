package com.example.specialserver.coccurent;

import com.example.specialserver.util.Logger;

import java.util.concurrent.Callable;

public class GuavaFutureDemo {

    public static final int SLEEP_GAP = 500;
    public static  String getCurThreadName(){
        return Thread.currentThread().getName();
    }

    //烧水
    static class HotWarterJob implements Callable<Boolean>{

        @Override
        public Boolean call() throws Exception {
            try {
                Logger.info("洗好水壶");
                Logger.info("灌上凉水");
                Logger.info("放在火上");
                //休眠一会，代表烧水中
                Thread.sleep(SLEEP_GAP);
                Logger.info("水烧开了....");
            }catch (Exception e){
                Logger.info("发生了异常，表示中断");
                return false;
            }
            Logger.info("运行结束.");
            return true;
        }
    }

    //烧水
    static class WashJob implements Callable<Boolean>{

        @Override
        public Boolean call() throws Exception {
            try {
                Logger.info("洗茶壶");
                Logger.info("洗茶杯");
                Logger.info("拿茶叶");
                //休眠一会，代表清洗中
                Thread.sleep(SLEEP_GAP);
                Logger.info("洗完了....");
            }catch (Exception e){
                Logger.info("发生了异常，表示清洗过程中被中断");
                return false;
            }
            Logger.info("清洗工作结束.");
            return true;
        }
    }

    //创建一个异步业务类型，作为泡茶喝主线程类
    static class MainJob implements Runnable {

        boolean warterOk = false;
        boolean cupOp = false;
        int gap = SLEEP_GAP / 10;

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(gap);
                    Logger.info("打游戏中.....");
                }catch (Exception e){
                    Logger.info("发生了异常，表示过程中被中断");
                }

                if (warterOk & cupOp){
                    drinkTea(warterOk,cupOp);
                }
            }
        }

        public void drinkTea (Boolean wok,Boolean cok){
            if (wok & cok){
                Logger.info("泡茶喝，茶喝完");
                this.warterOk = false;
                this.gap = SLEEP_GAP * 100;
            }else if (!wok){
                Logger.info("烧水失败，没有茶喝了");
            }else if (!cok){
                Logger.info("杯子洗不了，没有茶喝了");
            }
        }
    }


    public static void main(String[] args) {
        //创建一个新的线程实例，作为泡茶喝的主线程
        MainJob mainJob=new MainJob();
        Thread mainThread=new Thread(mainJob);
        mainThread.setName("主线程");
        mainThread.start();

        //烧水的业务逻辑实例
        Callable<Boolean> hotJob = new HotWarterJob();

        //清洗的业务逻辑实例
        Callable<Boolean> washJob = new WashJob();


//        //创建java线程池
//        ExecutorService jPool = Executors.newFixedThreadPool(10);
//        //构造guava线程池
//        ListeningExecutorService gPool = MoreExecutors.listeningDecorator(jPool);
//
//        //提交烧水的业务逻辑实例,到guava线程池获取异步任务
//        ListenableFuture<Boolean> hotFuture = gPool.submit(hotJob);

//        Futures.addCallback(hotFuture, new FutureCallback<Boolean>(){
//            public void onSuccess(Boolean aBoolean) {
//                if (aBoolean){
//                    mainJob.cupOp = true ;
//                }
//            }
//            public void onFailure(Throwable throwable) {
//                Logger.info("杯子洗不了，没有茶喝。");
//            }
//        });
//        //提交清洗的业务逻辑，取到异步任务
//        ListenableFuture<Boolean> washFuture = gPool.submit(washJob);
//        //绑定执行完的回调逻辑到异步任务
//        Futures.addCallback(washFuture, new FutureCallback<Boolean>() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                if (aBoolean){
//                    mainJob.cupOp = true ;
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Logger.info("杯子洗不了，没有茶喝。");
//            }
//        });
    }


}
