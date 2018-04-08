package com.zz.personal.controller;


import com.alibaba.fastjson.JSON;
import com.zz.personal.config.ActiceMqConfig;
import com.zz.personal.dao.entity.Photo;
import com.zz.personal.pojo.ResultDto;
import com.zz.personal.service.PhotoService;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@RestController
@RequestMapping("/springgame")
public class PhotoController {

    private Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PhotoService service;

    @RequestMapping("/detailphoto")
    public List<Photo> getPhoto(@RequestParam("id") Integer photoId){
        ActiceMqConfig acticeMqConfig = new ActiceMqConfig();
        Destination destination = new ActiveMQQueue("active.mq");
        acticeMqConfig.sendMessage(destination,"asda");
        return service.getOnePhoto(photoId);
    }

    @RequestMapping("/photolist")
    @Cacheable(value = "default",key = "'users_'+ #id")
    public String photoList(@RequestParam("id") Integer id){
        List<Photo> onePhoto = service.getOnePhoto(id);
        String s = JSON.toJSONString(onePhoto);
        return s;
    }

    @RequestMapping("/photocache")
    public ResultDto<String> photocache(@RequestParam("id") Integer id, @RequestParam("name") String name, HttpServletRequest request){
        Enumeration<?> pNames =  request.getParameterNames();
        ResultDto<String> resultDto = ResultDto.create();
        return resultDto.success("zzz");
//        String user_1 = redisTemplate.opsForValue().get("users_1");
//        return user_1;
    }



}
