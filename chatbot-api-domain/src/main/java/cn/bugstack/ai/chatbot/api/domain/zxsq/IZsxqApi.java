package cn.bugstack.ai.chatbot.api.domain.zxsq;

import cn.bugstack.ai.chatbot.api.domain.zxsq.model.aggregates.UnCommentsQuestionsAggregates;


public interface IZsxqApi {

    /**
     * 未回答信息
     * @param groupId
     * @param cookie
     * @return
     * @throws Exception
     */
    UnCommentsQuestionsAggregates queryUncommentsQuestionsTopics(String groupId, String cookie) throws Exception;

    /**
     * 回答函数
     * @param groupId
     * @param cookie
     * @param TopicId
     * @param text
     * @return
     * @throws Exception
     */
    Boolean answer(String groupId, String cookie, String TopicId,String text) throws Exception;
}
