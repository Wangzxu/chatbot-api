/**
  * Copyright 2025 bejson.com 
  */
package cn.bugstack.ai.chatbot.api.domain.ai.model.req;

import cn.bugstack.ai.chatbot.api.domain.ai.model.vo.Messages;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2025-02-05 15:37:15
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AiReq {

    private String model = "lite";
    private List<Messages> messages;

    public AiReq(String text) {
        List<Messages> messages = new ArrayList<>();
        messages.add(new Messages("user",text));
        this.messages = messages;
    }

}