package com.zz.personal.controller;

import com.zz.personal.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private Logger logger = LoggerFactory.getLogger(RedisController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private RedisServiceImpl redisService;

    private int buyCount = 20;

    private static final int TEST_NUM = 15;

    private static final String SELL_END_DATE = "2018-03-27 16:58:50";

    private CountDownLatch countDownLatch = new CountDownLatch(TEST_NUM);

    @RequestMapping("/miaosha")
    public void miaosha( ) {
        for (int i = 0; i < TEST_NUM; i++) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            Calendar calendar = Calendar.getInstance();
                            Calendar calendar1 = Calendar.getInstance();

                            try {
                                calendar1.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(SELL_END_DATE));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            redisService.isOrderSuccess(buyCount, (calendar1.getTime().getTime() - calendar.getTime().getTime()) / 1000);
                            try {
                                countDownLatch.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

            ).start();
        }

        countDownLatch.countDown();
    }
}
