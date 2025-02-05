package cn.bugstack.ai.chatbot.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动入口
 */
@SpringBootApplication
@EnableScheduling
public class ChatbotApiInterfaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbotApiInterfaceApplication.class, args);
    }

}
