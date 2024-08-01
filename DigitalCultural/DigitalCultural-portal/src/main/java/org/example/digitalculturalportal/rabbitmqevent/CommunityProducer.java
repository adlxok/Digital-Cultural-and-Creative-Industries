package org.example.digitalculturalportal.rabbitmqevent;

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
        rabbitTemplate.convertAndSend(RabbitMqConfig.MESSAGE_EXCHANGE,RabbitMqConfig.SYSTEM_MESSAGE_RK,communityEvent);
    }
}
