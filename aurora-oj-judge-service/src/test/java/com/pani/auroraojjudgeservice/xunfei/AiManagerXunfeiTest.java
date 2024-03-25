package com.pani.auroraojjudgeservice.xunfei;

import com.pani.auroraojjudgeservice.config.XingHuoConfig;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/3/13 18:46
 * @description
 */
@SpringBootTest
public class AiManagerXunfeiTest {

    private static SparkClient sparkClient;
    static{
        sparkClient = new SparkClient();
        sparkClient.apiKey = "b0ba24806677c9376fb3f61cca03d3eb";
        sparkClient.apiSecret = "MmM3NjE3NDQ3ODU1ODEzYzY3NzJhYTMz";
        sparkClient.appid = "0579be7c";
    }

    /**
     * AI 生成问题的预设条件
     */
//    public static final String PRECONDITION = "你是一个Java程序，接下来我会按照以下格式给你提供内容：程序输入：{在main方法args参数中接收，多轮输入中间用【分隔}，预期输出：{运行后的输出，多轮输出中间用【分隔}，代码：{代码，类名必须为Main}。\n" +
//            "你的返回纯文本是一个json字符串不要返回多余内容，字段为：status（程序正常运行为2,非正常运行为3），" +
//            "message（答案正确为Accepted，编译错误为Compile Error，答案错误为Wrong Answer（要严格按照格式，比预期输入多一个字都不行），" +
//            "运行错误Runtime Error），details（仿照java异常输出，没有则为空）";
    public static final String PRECONDITION = "你是一个Java判题专家，接下来我会按照以下格式给你提供内容：程序输入：{在main方法args参数中接收，多轮输入中间用【分隔}，代码：{代码，类名必须为Main}。\n" +
            "你的返回纯文本是一个json字符串不要返回多余内容，字段为：status（程序正常运行为2,非正常运行为3），" +
            "message（编译错误Compile Error，运行错误Runtime Error，不是则无），details（可能的异常详细信息，" +
            "仿照java异常输出，没有则为空），outputList（数组，每一轮输入该程序执行的输出，程序非正常运行则无）\n";

    static final String content = "程序输入：1 2【3 4\n" +
            "代码：public class Main {\n" +
            "    public static void main(String[] args) {\n" +
            "        int a = Integer.parseInt(args[4]);\n" +
            "        int b = Integer.parseInt(args[1]);\n" +
            "        System.out.println(\"结果:\" + (a + b));\n" +
//            "        Thread.sleep\"(80000);\n" +
            "    }\n" +
            "}\n";

    public static void main(String[] args) {
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传，默认为2048。
                // V1.5取值为[1,4096]
                // V2.0取值为[1,8192]
                // V3.0取值为[1,8192]
                .maxTokens(8192)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.1)
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        System.err.println("--------------");
        System.out.println(chatResponse.getContent());
    }
    @Test
    void testV35(){
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传，默认为2048。
                // V1.5取值为[1,4096]
                // V2.0取值为[1,8192]
                // V3.0取值为[1,8192]
                .maxTokens(8192)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.3)
                .apiVersion(SparkApiVersion.V3_5)
                .build();
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        System.err.println("--------------");
        System.out.println(chatResponse.getContent());


    }
}
