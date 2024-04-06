package com.pani.auroraojjudgeservice.judge.codesandbox.impl;

import cn.hutool.json.JSONUtil;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandbox;
import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.exception.BusinessException;
import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;
import com.pani.ojmodel.sandbox.JudgeInfo;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/3/18 19:30
 * @description ai判题！
 */
@Component
public class AiCodeSandbox implements CodeSandbox {
    @Resource
    private SparkClient sparkClient;

    public static final String PRECONDITION = "你是一个OJ判题机器，接下来我会按照以下格式给你提供内容：程序输入：{在main方法args参数中接收，多轮输入中间用“【【”分隔}，代码：{代码，类名必须为Main}。\n" +
            "你的返回纯文本是json字符串（不要返回多余内容）字段为：{status（程序正常运行为2,非正常运行为3），" +
            "message（编译错误为Compile Error，运行错误为Runtime Error，不是则无），details（可能的异常详细信息，" +
            "仿照java异常输出，没有则为空），outputList（数组，每一轮输入该程序执行的输出都要有，程序非正常运行则无）}\n";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String content = buildInput(executeCodeRequest);
        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages = new ArrayList<>();
        messages.add(SparkMessage.systemContent(PRECONDITION));
        messages.add(SparkMessage.userContent(content));
        // 构造请求
        SparkRequest sparkRequest = SparkRequest.builder()
                .messages(messages)
                .maxTokens(8192)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
//                .apiVersion(SparkApiVersion.V3_5)
                .apiVersion(SparkApiVersion.V2_0)
                .build();
        SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
        /*
        24 03 25 记录sparkClient空指针：不是@component，要去改一下工厂？
         */
        String chatResponseContent = chatResponse.getContent();
        if(chatResponseContent == null){
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "AI运行异常");
        }
        AiResponse aiResponse = JSONUtil.toBean(chatResponseContent, AiResponse.class);
        ExecuteCodeResponse executeCodeResponse = getExecuteCodeResponse(aiResponse);

        return executeCodeResponse;
    }

    @NotNull
    private ExecuteCodeResponse getExecuteCodeResponse(AiResponse aiResponse) {
        Integer status = aiResponse.getStatus();
        String detail = aiResponse.getDetail();
        String message = aiResponse.getMessage();
        List<String> outputList = aiResponse.getOutputList();

        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(outputList);
//        executeCodeResponse.setMessage(message);
        executeCodeResponse.setStatus(status);

        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(message);
        judgeInfo.setDetails(detail);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }

    private String buildInput(ExecuteCodeRequest executeCodeRequest){
        StringBuilder sb = new StringBuilder();
        sb.append("程序输入：");
        List<String> inputList = executeCodeRequest.getInputList();
        String code = executeCodeRequest.getCode();
        String join = StringUtils.join(inputList, "【【");
        sb.append(join).append("\n");
        sb.append("代码：").append(code);
        return sb.toString();
    }

    @Data
    static class AiResponse{
        Integer status;
        String detail;
        String message;
        List<String> outputList;
    }
}
