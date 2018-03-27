package com.zz.personal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl {

    private Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    private static final int GOODS_TOTAL_COUNT = 200;//商品总数量

    private static final String LOCK_KEY = "checkLock";//线程锁key

    private static final String SELL_COUNT_KEY = "sellCountKeyNew2";//redis中存放的已卖数量的key

    private static final int LOCK_EXPIRE = 6 * 1000; //锁占有时长

    /**
     * 检查下单是否成功
     * @param buyCount 购买数量
     * @param flashSellEndDate 截止时间
     * @return
     */
    public boolean isOrderSuccess(int buyCount,long flashSellEndDate) {

        if(flashSellEndDate <= 0){
            System.out.println("抢购活动已经结束:" + flashSellEndDate);
//            logger.info("抢购活动已经结束:" + flashSellEndDate);
            return false;
        }

        boolean resultFlag = false;
        try {
            if (redisLock(LOCK_KEY, LOCK_EXPIRE)) {
                Integer haveSoldCount = (Integer) this.getValueByKey(SELL_COUNT_KEY);
                Integer totalSoldCount = (haveSoldCount == null ? 0 : haveSoldCount) + buyCount;
                if (totalSoldCount <= GOODS_TOTAL_COUNT) {
                    this.setKeyValueWithExpire(SELL_COUNT_KEY, totalSoldCount, flashSellEndDate);
                    resultFlag = true;
                    System.out.println("已买数量： = " + totalSoldCount);
                    System.out.println("剩余数量：= " + (GOODS_TOTAL_COUNT - totalSoldCount));
                }else{
                    if(haveSoldCount < GOODS_TOTAL_COUNT){
                        System.out.println("对不起，您购买的数量已经超出商品库存数量，请重新下单.");
                    }else{
                        System.out.println("对不起，商品已售完.");
                    }
                }
                this.removeByKey(LOCK_KEY);
            } else {
                Integer soldCount = (Integer) this.getValueByKey(SELL_COUNT_KEY);
                if(soldCount != null && soldCount >= GOODS_TOTAL_COUNT){
                    //商品已经售完
                    System.out.println("all goods have sold out");
                    return false;
                }
                Thread.sleep(1000);//没有获取到锁 1s后重试
                return isOrderSuccess(buyCount,flashSellEndDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return resultFlag;
    }


    /**
     *  redis 锁
     * @param lock 锁的key
     * @param expire 锁的时长
     * @return
     */
    public Boolean redisLock(final String lock, final int expire) {

        return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                boolean locked = false;
                byte[] lockKeyName = redisTemplate.getStringSerializer().serialize(lock);
                byte[] lockValue = redisTemplate.getValueSerializer().serialize(getDateAferExpire(expire));
                locked = connection.setNX(lockKeyName, lockValue);
                if (locked){
                    connection.expire(lockKeyName, TimeoutUtils.toSeconds(expire, TimeUnit.MILLISECONDS));
                }
                return locked;
            }
        });
    }


    /**
     *
     *  判断key是否存在
     * @param key
     */
    public boolean existsKey(final String key) {

        return redisTemplate.hasKey(key);
    }

    /**
     * 获取指定时长后的Date
     * @param expireTime
     * @return
     */
    public Date getDateAferExpire(int expireTime){
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MILLISECOND, expireTime);

        return calendar.getTime();
    }

    /**
     * 根据key 获取对应的value
     *
     * @param key
     */
    public Object getValueByKey(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }
    /**
     * 删除指定的key
     *
     * @param key
     */
    public void removeByKey(final String key) {
        if (existsKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 设置带有指定时长的key value
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setKeyValueWithExpire(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            if (expireTime != null) {
                redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
