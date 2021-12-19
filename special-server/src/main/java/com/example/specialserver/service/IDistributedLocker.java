package com.example.specialserver.service;

import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public interface IDistributedLocker {

    /**
     * 通过key获取锁 {@link RLock}
     * @param lockKey 锁的key
     * @return org.redisson.api.RLock
     */
    RLock lock(String lockKey);

    /**
     * 通过key获取锁 {@link RLock}并设置一个超时时间
     * @param lockKey 锁的key
     * @param timeout 超时时间
     * @return org.redisson.api.RLock
     */
    RLock lock(String lockKey, int timeout);

    /**
     * 通过key获取锁 {@link RLock}并设置一个超时时间,并设置一个时间单位
     * @param lockKey 锁的key
     * @param unit 时间的单位
     * @param timeout 超时时间
     * @return org.redisson.api.RLock
     */
    RLock lock(String lockKey, TimeUnit unit, int timeout);

    /**
     * 通过key获取锁 {@link RLock}并设置一个超时时间,并设置一个时间单位和一个等待时间
     * @param lockKey
     * @param unit
     * @param waitTime
     * @param leaseTime
     * @return org.redisson.api.RLock
     */
    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    /**
     * <P>
     *     关闭锁
     * </P>
     * @param lockKey 锁的key
     */
    void unlock(String lockKey);

    /**
     * <P>
     *     关闭锁
     * </P>
     * @param lock 要关闭的锁
     */
    void unlock(RLock lock);
}
