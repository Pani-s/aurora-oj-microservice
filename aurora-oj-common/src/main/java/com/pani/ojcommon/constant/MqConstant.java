package com.pani.ojcommon.constant;

/**
 * @author Pani
 * @date Created in 2024/3/17 14:15
 * @description 关于消息队列的
 */
public interface MqConstant {
    //region判题
    String JUDGE_QUEUE_NAME = "judge_queue";
    String JUDGE_EXCHANGE_NAME = "judge_exchange";
    String JUDGE_ROUTING_KEY = "judge_routingKey";
    /**
     * 死信
     */
    String JUDGE_DLX_EXCHANGE = "judge_dlx_exchange";
    String JUDGE_DLX_QUEUE = "judge_dlx_queue";
    String JUDGE_DLX_ROUTING = "judge_dlx_routing_key";
    //endregion
    //region 题目ac+1
    String QUESTION_QUEUE_NAME = "question_queue";
    String QUESTION_EXCHANGE_NAME = "question_exchange";
    String QUESTION_ROUTING_KEY = "question_routingKey";
    //endregion
}
