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
//    public static final String COMMENT_QUEUE="comment_queue";
//    public static final String FOLLOW_QUEUE="follow_queue";
    //路由
    public static final String SYSTEM_MESSAGE_RK="system_message_rk";
//    public static final String COMMENT_RK="comment_rk";
//    public static final String FOLLOW_RK="follow_rk";
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
//    @Bean
//    public Queue commentQueue(){
//        return QueueBuilder.durable(COMMENT_QUEUE).build();
//    }
//    @Bean
//    public Queue followQueue(){
//        return QueueBuilder.durable(FOLLOW_QUEUE).build();
//    }
    //绑定交换机和路由
    @Bean
    public Binding queue1BindingExchange(@Qualifier("systemMessageQueue") Queue systemMessageQueue,
                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
        return BindingBuilder.bind(systemMessageQueue).to(messageExchange).with(SYSTEM_MESSAGE_RK);
    }
//    @Bean
//    public Binding queue2BindingExchange(@Qualifier("commentQueue") Queue commentQueue,
//                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
//        return BindingBuilder.bind(commentQueue).to(messageExchange).with(COMMENT_RK);
//    }
//    @Bean
//    public Binding queue3BindingExchange(@Qualifier("followQueue") Queue followQueue,
//                                         @Qualifier("messageExchange") DirectExchange messageExchange ){
//        return BindingBuilder.bind(followQueue).to(messageExchange).with(FOLLOW_RK);
//    }

}
