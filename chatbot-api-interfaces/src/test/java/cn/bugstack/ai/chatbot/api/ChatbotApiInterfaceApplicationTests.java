package cn.bugstack.ai.chatbot.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 单元测试
 */
@SpringBootApplication
@SpringBootTest
class ChatbotApiInterfaceApplicationTests {

    @Test
    void contextLoads() {
    }
    @Value("${chatbot-api.xf-key}")
    private String pwd;
    @Test
    public void testChatgpt() throws IOException, InterruptedException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://spark-api-open.xf-yun.com/v1/chat/completions");
        post.setHeader("Content-Type","application/json");
        post.setHeader("Authorization", pwd);

        String paramJson = "{\n" +
                "    \"model\":\"lite\",\n" +
                "    \"messages\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"content\": \"来一个只有程序员能听懂的笑话\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"stream\": false\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.APPLICATION_JSON);
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
        else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
