package cn.bugstack.ai.chatbot.api.domain.ai.service;

import cn.bugstack.ai.chatbot.api.domain.ai.IXfAi;
import cn.bugstack.ai.chatbot.api.domain.ai.model.aggregates.AiAnswer;
import cn.bugstack.ai.chatbot.api.domain.ai.model.req.AiReq;
import cn.bugstack.ai.chatbot.api.domain.ai.model.vo.Choices;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author w'z'x
 * @version 1.0
 * @description: 讯飞Ai接口
 * @date 2025/2/5 15:19
 */
@Service
public class XfAi implements IXfAi {

    private Logger logger = LoggerFactory.getLogger(XfAi.class);

    @Override
    public String doXfchat(String question, String xfKey) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://spark-api-open.xf-yun.com/v1/chat/completions");
        post.setHeader("Content-Type","application/json");
        post.setHeader("Authorization", xfKey);

//        String paramJson = "{\n" +
//                "    \"model\":\"lite\",\n" +
//                "    \"messages\": [\n" +
//                "        {\n" +
//                "            \"role\": \"user\",\n" +
//                "            \"content\": \"来一个只有程序员能听懂的笑话\"\n" +
//                "        }\n" +
//                "    ],\n" +
//                "    \"stream\": false\n" +
//                "}";
        AiReq req = new AiReq(question);
        String paramJson = JSON.toJSONString(req);

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            logger.info("提问为：{},AI的回答为:{}" , question,res);
            AiAnswer aiAnswer = JSON.parseObject(res, AiAnswer.class);
            List<Choices> choices = aiAnswer.getChoices();
            StringBuilder answer = new StringBuilder();
            for (Choices choice : choices) {
                answer.append(choice.getMessage().getContent());
            }
            return answer.toString();
        }
        else{
            throw new RuntimeException("AIanswer error code is" + response.getStatusLine().getStatusCode());
        }
    }
}
