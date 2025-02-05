package cn.bugstack.ai.chatbot.api.domain.zxsq.service;

import cn.bugstack.ai.chatbot.api.domain.zxsq.IZsxqApi;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.aggregates.UnCommentsQuestionsAggregates;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.req.AnswerReq;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.req.ReqData;
import cn.bugstack.ai.chatbot.api.domain.zxsq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author w'z'x
 * @version 1.0
 * @description: 知识星球api实现类
 * @date 2025/2/3 11:38
 */
@Service
public class ZsxqApi implements IZsxqApi {
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);
    @Override
    public UnCommentsQuestionsAggregates queryUncommentsQuestionsTopics(String groupId, String cookie) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId +"/topics?scope=all&count=20");
        get.setHeader("cookie",cookie);
        get.setHeader("Content-Type", "application/json; charset=utf-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据：GroupId:{},answer:{}",groupId, res);
            //todo talk中的text需要Unicode转义
            return JSON.parseObject(res,UnCommentsQuestionsAggregates.class);
        }else{
            throw new RuntimeException("UnCommentsQuestionsAggregates get error code is" + response.getStatusLine().getStatusCode());
        }
    }

    @Override
    public Boolean answer(String groupId, String cookie, String TopicId, String text) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //todo 以topicId为准
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+TopicId+"/comments");
        post.setHeader("cookie",cookie);
        post.setHeader("Content-Type", "application/json; charset=utf-8");

//        post.addHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36");


//        String paramJson = "{\"req_data\":{\"text\":\"" + text+"\\n\",\"image_ids\":[],\"mentioned_user_ids\":[]}}";

        String textWithNewline = text;  // 直接在文本后添加换行符
        AnswerReq answerReq = new AnswerReq(new ReqData(textWithNewline));
        String paramJson = JSON.toJSONString(answerReq);
//        System.out.println(paramJson);
//        System.out.println(paramJson);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("application/json", "UTF-8"));

        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            logger.info("知识星球的回答为：TopicId:{},GroupId:{},answer:{}",TopicId,groupId, res);
            AnswerRes answerRes = JSON.parseObject(res, AnswerRes.class);
            return answerRes.getSucceeded();
        }else{
            throw new RuntimeException("answer error code is" + response.getStatusLine().getStatusCode());
        }
    }
}
