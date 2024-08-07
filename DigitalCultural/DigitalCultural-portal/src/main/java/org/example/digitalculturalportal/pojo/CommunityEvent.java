package org.example.digitalculturalportal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 社区消息对象（用于系统通知）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityEvent {
    /**
     * 事件(队列）类型
     */
    private String eventType;
    /**
     * 谁触发了事件
     */
    private Integer userId;
    /**
     * 实体id
     */
    private Integer entityId;


    /**
     * 实体类型
     */
    private Integer EntityType;
    /**
     * 实体作者（该消息发送给他）
     */
    private Integer entityUserId;
    /**
     * 存储数据
     */
    private Map<String,Object> data=new HashMap<>();
    public Map<String, Object> getData() {
        return data;
    }

    public CommunityEvent setData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
