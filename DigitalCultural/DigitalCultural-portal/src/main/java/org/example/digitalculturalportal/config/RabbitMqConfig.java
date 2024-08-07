package org.example.digitalculturalportal.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    //交换机名称
    public static final String MESSAGE_EXCHANGE="message_exchange";
    //点赞，评论，关注队列
    public static final String SYSTEM_MESSAGE_QUEUE="system_message_queue";
    //发帖队列
    public static final String ADD_POST_QUEUE="add_post_queue";
    //删帖队列
    public static final String DELETE_POST_QUEUE="delete_post_queue";

    //路由
    public static final String SYSTEM_MESSAGE_RK="system_message_rk";
    public static final String ADD_POST_RK="add_post_rk";
    public static final String DELETE_POST_RK="delete_post_rk";
    //声明交换机
    @Bean
    public DirectExchange messageExchange(){
        return ExchangeBuilder.directExchange(MESSAGE_EXCHANGE).durable(true).build();
    }
    //声明队列
    @Bean
    public Queue systemMessageQueue(){
        return QueueBuilder.durable(SYSTEM_MESSAGE_QUEUE).build();
    }
    @Bean
    public Queue addPostQueue(){
        return QueueBuilder.durable(ADD_POST_QUEUE).build();
    }
    @Bean
    public Queue deletePostQueue(){
        return QueueBuilder.durable(DELETE_POST_QUEUE).build();
    }
    //绑定交换机和路由
    @Bean
    public Binding queue1BindingExchange(@Qualifier("systemMessageQueue") Queue systemMessageQueue,
                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
        return BindingBuilder.bind(systemMessageQueue).to(messageExchange).with(SYSTEM_MESSAGE_RK);
    }
    @Bean
    public Binding queue2BindingExchange(@Qualifier("addPostQueue") Queue addPostQueue,
                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
        return BindingBuilder.bind(addPostQueue).to(messageExchange).with(ADD_POST_RK);
    }
    @Bean
    public Binding queue3BindingExchange(@Qualifier("deletePostQueue") Queue deletePoetQueue,
                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
        return BindingBuilder.bind(deletePoetQueue).to(messageExchange).with(DELETE_POST_RK);
    }

}
