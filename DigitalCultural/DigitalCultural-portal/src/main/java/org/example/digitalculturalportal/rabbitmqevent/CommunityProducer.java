package org.example.digitalculturalportal.rabbitmqevent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.digitalculturalportal.config.RabbitMqConfig;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 社区消息队列的生产者
 * @author jisamin
 * @since 2024/7/30
 */
@Component
public class CommunityProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendEvent(CommunityEvent communityEvent){
        ObjectMapper mapper=new ObjectMapper();
        try {
            //将对象转换为json字符串
            String jsonStr=mapper.writeValueAsString(communityEvent);
            rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE, RabbitMqConfig.SYSTEM_MESSAGE_RK, jsonStr);
        }catch (Exception e){
            System.out.println("发送消息失败"+e);
        }
    }
    public void sendAddPostEvent(CommunityEvent communityEvent){
        ObjectMapper mapper=new ObjectMapper();
        try {
            //将对象转换为json字符串
            String jsonStr=mapper.writeValueAsString(communityEvent);
                rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE, RabbitMqConfig.ADD_POST_RK, jsonStr);
        }catch (Exception e){
            System.out.println("发送消息失败"+e);
        }
    }
    public void sendDeletePostEvent(CommunityEvent communityEvent){
        ObjectMapper mapper=new ObjectMapper();
        try {
            //将对象转换为json字符串
            String jsonStr=mapper.writeValueAsString(communityEvent);
            rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE, RabbitMqConfig.DELETE_POST_RK, jsonStr);
        }catch (Exception e){
            System.out.println("发送消息失败"+e);
        }
    }
}
