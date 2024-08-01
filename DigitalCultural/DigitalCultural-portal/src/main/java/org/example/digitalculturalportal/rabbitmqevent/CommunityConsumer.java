package org.example.digitalculturalportal.rabbitmqevent;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.config.RabbitMqConfig;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.example.digitalculturalportal.pojo.CommunityMessage;
import org.example.digitalculturalportal.service.CommunityMessageService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 社区消息队列的消费者
 * @author jisamin
 * @since 2024/7/30
 */
@Component
@Slf4j
public class CommunityConsumer implements CommunityConstant {
    @Autowired
    private CommunityMessageService communityMessageService;
    @RabbitListener(queues = RabbitMqConfig.SYSTEM_MESSAGE_QUEUE)
    public void handleSystemMsg(Message message)  {
      if(message==null){
          log.error("消息内容为空");
          return;
      }
      CommunityEvent communityEvent= JSONObject.parseObject(message.getBody(),CommunityEvent.class);
      if(communityEvent==null){
          log.error("消息转换失败");
      }
      //发送系统通知
      CommunityMessage communityMessage=new CommunityMessage();
      communityMessage.setFormId(SYSTEM_USER_ID);//代表系统发送的通知
      communityMessage.setToId(communityEvent.getEntityUserId());
      communityMessage.setConversationId(communityEvent.getEventType());
      communityMessage.setCreateTime(new Date());

        Map<String,Object> content=new HashMap<>();
        content.put("userId",communityEvent.getUserId());
        content.put("entityType",communityEvent.getEntityType());
        content.put("entityId",communityEvent.getEntityId());
        if(!communityEvent.getData().isEmpty()){
            for (Map.Entry<String, Object> entry : communityEvent.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }
        communityMessage.setContent(JSONObject.toJSONString(content));
        communityMessageService.addMessage(communityMessage);
    }

}
