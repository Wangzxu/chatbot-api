package cn.bugstack.ai.chatbot.api;

import cn.bugstack.ai.chatbot.api.domain.ai.IXfAi;
import cn.bugstack.ai.chatbot.api.domain.zxsq.IZsxqApi;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.aggregates.UnCommentsQuestionsAggregates;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.vo.Topics;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;
    @Value("${chatbot-api.xf-key}")
    private String xfkey;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IXfAi xfAi;

    @Test
    public void test_zsxqApi() throws Exception {
        UnCommentsQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUncommentsQuestionsTopics(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
        List<Topics> topics = unAnsweredQuestionsAggregates.getRespdata().getTopics();
        for (Topics topic : topics) {
            if(topic.getComments_count() != 1){
                continue;
            }
            String topicId = topic.getTopic_id();
            String text = topic.getTalk().getText();
            logger.info("topicId:{}, text:{}", topicId, text);

            String ans = "TEST1";
            zsxqApi.answer(groupId, cookie, topicId, ans);
        }
    }

    @Test
    public void test_AiApi() throws Exception {
        String question = "你几岁了";
        System.out.println(xfAi.doXfchat(question, xfkey));
    }
    }




