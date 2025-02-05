package cn.bugstack.ai.chatbot.api.application.job;

import cn.bugstack.ai.chatbot.api.domain.ai.IXfAi;
import cn.bugstack.ai.chatbot.api.domain.zxsq.IZsxqApi;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.aggregates.UnCommentsQuestionsAggregates;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.vo.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

/**
 * @author w'z'x
 * @version 1.0
 * @description: 问题任务
 * @date 2025/2/5 17:00
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule {

    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

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

    @Scheduled(cron = " 0/5 * * * * ?")
    public void run() {
        try {
            if(new Random().nextBoolean()){
                logger.info("随即打样中....");
                return;
            }
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
            if(hour > 22 || hour < 7){
                logger.info("AI下班了，不工作");
                return;
            }

            // 每分钟获取一次未回答问题
            UnCommentsQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUncommentsQuestionsTopics(groupId, cookie);
//            logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));
            List<Topics> topics = unAnsweredQuestionsAggregates.getRespdata().getTopics();
            // 判断问题是否存在
            if(topics == null || topics.isEmpty()) {
                logger.info("本次未检查到未回答的问题");
                return;
            }
            int k = 0;
            for (Topics topic : topics) {
                if (topic.getComments_count() == 0) {
                    // 获取问题和话题号码
                    String topicId = topic.getTopic_id();
                    String text = topic.getTalk().getText();
                    logger.info("topicId:{}, text:{}", topicId, text);
                    // 询问大模型
                    String ans = xfAi.doXfchat(text, xfkey);
                    // 回答具体问题
                    Boolean status = zsxqApi.answer(groupId, cookie, topicId, ans);
                    logger.info("问题编号:{}, 问题内容:{},回答结果:{},回答状态:{}", topicId, text, ans,status);
                    k++;
                    return;
                }
            }
            if(k==0){
                logger.info("本次未检查到未回答的问题");
            }
        } catch (Exception e) {
            logger.error("自动回答问题异常",e);
            throw new RuntimeException(e);
        }
    }
}