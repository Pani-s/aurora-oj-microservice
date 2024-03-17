package com.pani.auroraojquestionservice.rabbitmq;

import com.pani.ojcommon.constant.MqConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/17 14:16
 * @description
 */
@Component
public class JudgeMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param message question submit id
     */
    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(MqConstant.EXCHANGE_NAME, MqConstant.ROUTING_KEY, message);
    }

}
