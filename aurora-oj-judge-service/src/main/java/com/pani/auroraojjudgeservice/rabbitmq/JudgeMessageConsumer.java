package com.pani.auroraojjudgeservice.rabbitmq;

import com.pani.auroraojjudgeservice.judge.JudgeService;
import com.pani.ojcommon.constant.MqConstant;
import com.rabbitmq.client.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/17 14:19
 * @description
 */
@Component
@Slf4j
public class JudgeMessageConsumer {
    @Resource
    private JudgeService judgeService;

    /**
     *     指定程序监听的消息队列和确认机制
      */
    @SneakyThrows
    @RabbitListener(queues = {MqConstant.QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            channel.basicNack(deliveryTag, false, false);
        }
    }

}
