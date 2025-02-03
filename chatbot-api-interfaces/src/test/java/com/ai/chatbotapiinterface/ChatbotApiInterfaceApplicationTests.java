package com.ai.chatbotapiinterface;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * 单元测试
 */
@SpringBootTest
class ChatbotApiInterfaceApplicationTests {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88888144184512/topics?scope=all&count=20");
        get.setHeader("cookie","zsxq_access_token=EC7C3B38-320A-DAE1-92FA-4825862C979E_8491214BC4B99C43; zsxqsessionid=281171ea70e85512a1408af21363d18a; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22812244852181182%22%2C%22first_id%22%3A%22194c718f7cc120-0912f684bda12f8-4c657b58-1821369-194c718f7cd1b9d%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk0YzcxOGY3Y2MxMjAtMDkxMmY2ODRiZGExMmY4LTRjNjU3YjU4LTE4MjEzNjktMTk0YzcxOGY3Y2QxYjlkIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODEyMjQ0ODUyMTgxMTgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22812244852181182%22%7D%2C%22%24device_id%22%3A%22194c718f7cc120-0912f684bda12f8-4c657b58-1821369-194c718f7cd1b9d%22%7D");
        get.setHeader("Content-Type", "application/json; charset=utf-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            //todo talk中的text需要Unicode转义
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        //todo 以topicId为准
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/8858154212111542/comments");
        post.setHeader("cookie","zsxq_access_token=EC7C3B38-320A-DAE1-92FA-4825862C979E_8491214BC4B99C43; zsxqsessionid=281171ea70e85512a1408af21363d18a; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22812244852181182%22%2C%22first_id%22%3A%22194c718f7cc120-0912f684bda12f8-4c657b58-1821369-194c718f7cd1b9d%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTk0YzcxOGY3Y2MxMjAtMDkxMmY2ODRiZGExMmY4LTRjNjU3YjU4LTE4MjEzNjktMTk0YzcxOGY3Y2QxYjlkIiwiJGlkZW50aXR5X2xvZ2luX2lkIjoiODEyMjQ0ODUyMTgxMTgyIn0%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22812244852181182%22%7D%2C%22%24device_id%22%3A%22194c718f7cc120-0912f684bda12f8-4c657b58-1821369-194c718f7cd1b9d%22%7D");
        post.setHeader("Content-Type", "application/json; charset=utf-8");

        String paramJson = "{\"req_data\":{\"text\":\"我不会\\n\",\"image_ids\":[],\"mentioned_user_ids\":[]}}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("json/text", "UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
