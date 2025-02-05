/**
  * Copyright 2025 bejson.com 
  */
package cn.bugstack.ai.chatbot.api.domain.zxsq.model.vo;
import lombok.Data;

import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2025-02-03 11:17:21
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Topics {

    private String topic_id;
    private Group group;
    private String type;
    private Talk talk;
    private List<ShowComments> show_comments;
    private String likes_count;
    private String tourist_likes_count;
    private String rewards_count;
    private Integer comments_count;
    private String reading_count;
    private String readers_count;
    private boolean digested;
    private boolean sticky;
    private Date create_time;
    private UserSpecific user_specific;
    private String title;

}