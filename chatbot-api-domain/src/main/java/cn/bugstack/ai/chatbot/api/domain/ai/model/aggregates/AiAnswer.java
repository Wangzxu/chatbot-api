/**
  * Copyright 2025 bejson.com 
  */
package cn.bugstack.ai.chatbot.api.domain.ai.model.aggregates;
import cn.bugstack.ai.chatbot.api.domain.ai.model.vo.Choices;
import cn.bugstack.ai.chatbot.api.domain.ai.model.vo.Usage;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2025-02-05 15:24:16
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class AiAnswer {

    private int code;
    private String message;
    private String sid;
    private List<Choices> choices;
    private Usage usage;

}