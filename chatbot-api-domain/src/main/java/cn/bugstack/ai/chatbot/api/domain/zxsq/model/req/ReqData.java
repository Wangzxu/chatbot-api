/**
  * Copyright 2025 bejson.com 
  */
package cn.bugstack.ai.chatbot.api.domain.zxsq.model.req;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Auto-generated: 2025-02-04 12:36:48
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
@AllArgsConstructor
public class ReqData {



    private String[] mentioned_user_ids = new String[]{};
    private String text;
    private String[] image_ids = new String[]{};

    public ReqData(String text) {
        this.text = text;
    }

}