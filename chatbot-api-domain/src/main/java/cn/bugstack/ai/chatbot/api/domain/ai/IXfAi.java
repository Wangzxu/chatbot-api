package cn.bugstack.ai.chatbot.api.domain.ai;

import java.io.IOException;

public interface IXfAi {
    public String doXfchat(String question,String xfKey)throws IOException;
}
