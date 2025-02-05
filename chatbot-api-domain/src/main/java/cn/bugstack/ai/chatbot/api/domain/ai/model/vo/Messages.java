/**
  * Copyright 2025 bejson.com 
  */
package cn.bugstack.ai.chatbot.api.domain.ai.model.vo;

import lombok.Data;

/**
 * Auto-generated: 2025-02-05 15:37:15
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */

/**
 * 请求体
 */
@Data
public class Messages {

    private String role;
    private String content;

    public Messages(String user, String text) {
        this.role = user;
        this.content = text;
    }
}