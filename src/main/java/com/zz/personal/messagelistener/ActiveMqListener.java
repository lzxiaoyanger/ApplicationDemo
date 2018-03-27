package com.zz.personal.messagelistener;


import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.stereotype.Component;

import javax.jms.ConnectionFactory;

@Component
public class ActiveMqListener {

    //生产者消费者
    @JmsListener(destination = "active.mq")
    public void reciveMqMessage(String message){
        System.out.println("收到的消息"+ message);

    }

    //发布者订阅者
    @JmsListener(destination = "active.mq",containerFactory = "myJmsContainerFactory")
    public void reciveMqMessage2(String message){
        System.out.println("收到的消息"+ message);

    }

    @Bean
    JmsListenerContainerFactory<?> myJmsContainerFactory(ConnectionFactory connectionFactory){
        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true);
        return factory;
    }



}
