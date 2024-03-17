package com.pani.auroraojjudgeservice.rabbitmq;
import com.pani.ojcommon.constant.MqConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pani
 * @date Created in 2024/3/17 14:21
 * @description
 */
@Slf4j
@Component
public class InitRabbitMqBean {
    @Value("${spring.rabbitmq.host:localhost}")
    private String host;

    @PostConstruct
    public void init() {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(host);
            //部署的时候还需要账号密码 > .<

            try(Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()){
            channel.exchangeDeclare(MqConstant.EXCHANGE_NAME, "direct");

            // 创建队列
            channel.queueDeclare(MqConstant.QUEUE_NAME, true, false, false, null);
            channel.queueBind(MqConstant.QUEUE_NAME, MqConstant.EXCHANGE_NAME,
                    MqConstant.ROUTING_KEY);
            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败", e);
        }
    }

}
