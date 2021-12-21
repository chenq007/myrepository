package com.example.specialserver.controller;

import com.example.specialserver.service.IDistributedLocker;
import com.example.specialserver.util.Logger;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("shop")
public class ShopController {

    static String keys = "count";

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private IDistributedLocker distributedLocker;

    @GetMapping("set")
    public String setShopCount(){
        redisTemplate.opsForValue().set(keys,100);
        return "成功！";
    }

    @PostMapping("shoping/first")
    public void  shopingfirst(){
        try {
                Integer count = (Integer) redisTemplate.opsForValue().get(keys);
                if (count > 0){
                    redisTemplate.opsForValue().set(keys,count-1);
                    System.out.println("成功抢到商品，库存还剩下："+ (count-1));
                }else {
                    System.out.println("商品已经抢购完!!!!!!!!!!!");
                }
            System.out.println("交易火爆中，请稍后重试！！！");
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }
    }

    @PostMapping("shoping/first1")
    public void  shopingfirst1(){
        try {
        synchronized (this){
            Integer count = (Integer) redisTemplate.opsForValue().get(keys);
            if (count > 0){
                redisTemplate.opsForValue().set(keys,count-1);
                System.out.println("成功抢到商品，库存还剩下："+ (count-1));
            }else {
                System.out.println("商品已经抢购完!!!!!!!!!!!");
            }
            System.out.println("交易火爆中，请稍后重试！！！");
        }
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }
    }


    @PostMapping("shoping/second")
    public void  shopingsecond(){
        String lockkey = "lockkey";
        String uuidString = UUID.randomUUID().toString();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockkey, uuidString, 500, TimeUnit.MILLISECONDS);
        try {
            if (aBoolean){
                Integer count = (Integer) redisTemplate.opsForValue().get(keys);
                if (count > 0){
                    redisTemplate.opsForValue().set(keys,count-1);
                    System.out.println("成功抢到商品，库存还剩下："+ (count-1));
                }else {
                    System.out.println("商品已经抢购完!!!!!!!!!!!");
                }
            }
            System.out.println("交易火爆中，请稍后重试！！！");
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }
    }


    @PostMapping("shoping/three")
    public void  shopingThree(){
        String lockkey = "lockkey";
        String uuidString = UUID.randomUUID().toString();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockkey, uuidString, 10, TimeUnit.MILLISECONDS);
        try {
            if (aBoolean){
                Integer count = (Integer) redisTemplate.opsForValue().get(keys);
                Thread.sleep(10);
                if (count > 0){
                    redisTemplate.opsForValue().set(keys,count-1);
                    System.out.println("成功抢到商品，库存还剩下："+ (count-1));
                }else {
                    System.out.println("商品已经抢购完!!!!!!!!!!!");
                }
            }
            System.out.println("交易火爆中，请稍后重试！！！");
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }finally {
                redisTemplate.delete(lockkey);
        }
    }

    @PostMapping("shoping/four")
    public void  shopingFour(){
        String lockkey = "lockkey";
        String uuidString = UUID.randomUUID().toString();
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent(lockkey, uuidString, 10, TimeUnit.MILLISECONDS);
        try {
            if (aBoolean){
                Integer count = (Integer) redisTemplate.opsForValue().get(keys);
                Thread.sleep(5);
                if (count > 0){
                    redisTemplate.opsForValue().set(keys,count-1);
                    System.out.println("成功抢到商品，库存还剩下："+ (count-1));
                }else {
                    System.out.println("商品已经抢购完!!!!!!!!!!!");
                }
            }
            System.out.println("交易火爆中，请稍后重试！！！");
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }finally {
            if (uuidString.equals(redisTemplate.opsForValue().get(lockkey))){
                redisTemplate.delete(lockkey);
            }
        }
    }

    @PostMapping("shoping/five")
    public void   shopingFive(){
        String lockkey = "lockkey";
        RLock lock = distributedLocker.lock(lockkey);
        try {
            lock.lock(10,TimeUnit.MILLISECONDS);
            Thread.sleep(5);
            Integer count = (Integer) redisTemplate.opsForValue().get(keys);
            Logger.info("lock:" + lock.toString() + ",interrupted:" + Thread.currentThread().isInterrupted()
                    + ",hold:" + lock.isHeldByCurrentThread() + ",threadId:" + Thread.currentThread().getId());
                if (count > 0){
                    redisTemplate.opsForValue().set(keys,count-1);
                    System.out.println("成功抢到商品，库存还剩下："+ (count-1));
                }else {
                    System.out.println("商品已经抢购完!!!!!!!!!!!");
                }
        }catch (Exception e){
            System.out.println("服务异常!!!!!!!!!!!");
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
