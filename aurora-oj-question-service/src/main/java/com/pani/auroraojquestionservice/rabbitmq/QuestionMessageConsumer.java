package com.pani.auroraojquestionservice.rabbitmq;

import com.pani.auroraojquestionservice.service.QuestionService;
import com.pani.auroraojquestionservice.service.QuestionSubmitService;
import com.pani.auroraojquestionservice.service.UserSubmitService;
import com.pani.ojcommon.constant.MqConstant;
import com.pani.ojmodel.entity.QuestionSubmit;
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
public class QuestionMessageConsumer {

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionSubmitService questionSubmitService;
    @Resource
    private UserSubmitService userSubmitService;

    /**
     * 收到消息，该题目通过数加一，然后该用户user_first_submit
     */
    @SneakyThrows
    @RabbitListener(queues = {MqConstant.QUESTION_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("题目AC+1的消息队列收到消息 = {}", message);
        //该题目，通过数加一

        QuestionSubmit questionSubmit = questionSubmitService.getById(Long.parseLong(message));
        if (questionSubmit == null) {
            log.error("题目提交 数据库中没找到 = {}", message);
            channel.basicAck(deliveryTag, false);
        }

        Long questionId = questionSubmit.getQuestionId();
        if (!questionService.incrAcNum(questionId)) {
            log.error("题目通过数信息更新错误: {}", questionId);
            channel.basicAck(deliveryTag, false);
        } else {
            log.info("题目通过数+1: {}", questionId);
        }


        //记录用户通过
        if (!userSubmitService.updateUserSubmitRecord(questionId, questionSubmit.getUserId())) {
            log.error("用户题目通过记录信息更新错误");
        }
        log.info("用户{}题目通过记录+1: {}", questionSubmit.getUserId(), questionId);

        channel.basicAck(deliveryTag, false);

    }


}
