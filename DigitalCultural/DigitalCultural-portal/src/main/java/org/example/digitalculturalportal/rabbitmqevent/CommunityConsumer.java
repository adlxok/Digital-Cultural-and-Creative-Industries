package org.example.digitalculturalportal.rabbitmqevent;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.digitalculturalportal.config.RabbitMqConfig;
import org.example.digitalculturalportal.pojo.CommunityEvent;
import org.example.digitalculturalportal.pojo.CommunityMessage;
import org.example.digitalculturalportal.pojo.CommunityPost;
import org.example.digitalculturalportal.service.CommunityMessageService;
import org.example.digitalculturalportal.service.CommunityPostService;
import org.example.digitalculturalportal.service.ElasticsearchService;
import org.example.digitalculturalportal.utils.CommunityConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private ElasticsearchService elasticsearchService;

    //消费系统通知
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.SYSTEM_MESSAGE_QUEUE)
    public void handleSystemMsg(String message) {
        if (message == null) {
            log.error("消息内容为空");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(message);
            CommunityEvent communityEvent = mapper.convertValue(rootNode, CommunityEvent.class);


            //发送系统通知
            CommunityMessage communityMessage = new CommunityMessage();
            communityMessage.setFromId(SYSTEM_USER_ID);//1代表系统发送的通知
            communityMessage.setToId(communityEvent.getEntityUserId());
            communityMessage.setConversationId(communityEvent.getEventType());
            communityMessage.setCreateTime(new Date());

            Map<String, Object> content = new HashMap<>();
            content.put("userId", communityEvent.getUserId());
            content.put("entityType", communityEvent.getEntityType());
            content.put("entityId", communityEvent.getEntityId());
            //对哪个帖子操作
            if (!communityEvent.getData().isEmpty()) {
                for (Map.Entry<String, Object> entry : communityEvent.getData().entrySet()) {
                    content.put(entry.getKey(), entry.getValue());
                }
            }
            communityMessage.setContent(JSONObject.toJSONString(content));
            communityMessageService.addMessage(communityMessage);
        } catch (Exception e) {
            System.out.println("消息转换失败 :" + e);
        }

    }

    //消费发帖
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.ADD_POST_QUEUE)
    public void handleAddPost(String message) {
        if (message == null) {
            log.error("消息内容为空");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(message);
            CommunityEvent communityEvent = mapper.convertValue(rootNode, CommunityEvent.class);
            //向elasticsearch添加帖子
            CommunityPost communityPost=communityPostService.queryCommunityPostById(communityEvent.getEntityId());
            elasticsearchService.saveCommunityPost(communityPost);
        } catch (Exception e) {
            System.out.println("消息转换失败" + e);
        }
    }

    //消费删帖
    @RabbitHandler
    @RabbitListener(queues = RabbitMqConfig.DELETE_POST_QUEUE)
    public void handleDeletePost(String message) {
        if (message == null) {
            log.error("消息内容为空");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(message);
            CommunityEvent communityEvent = mapper.convertValue(rootNode, CommunityEvent.class);
            //从elasticsearch删除帖子
            elasticsearchService.deleteCommunityPost(communityEvent.getEntityId());
        } catch (Exception e) {
            System.out.println("消息转换失败" + e);
        }
    }

}
