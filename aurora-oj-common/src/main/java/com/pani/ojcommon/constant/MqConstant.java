package com.pani.ojcommon.constant;

/**
 * @author Pani
 * @date Created in 2024/3/17 14:15
 * @description 关于消息队列的
 */
public interface MqConstant {
    //region判题
    String QUEUE_NAME = "judge_queue";
    String EXCHANGE_NAME = "judge_exchange";
    String ROUTING_KEY = "judge_routingKey";
    //endregion
    //region死信
    /**
     * 死信
     */
    String DLX_EXCHANGE_NAME = "judge_dlx_exchange";
    String DLX_QUEUE_NAME = "judge_dlx_queue";
    String DLX_ROUTING_KEY = "judge_dlx_routing_key";
    //endregion
}
