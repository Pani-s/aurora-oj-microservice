package com.pani.auroraojjudgeservice.judge.codesandbox.impl;


import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandbox;
import com.pani.ojmodel.enums.JudgeInfoMessageEnum;
import com.pani.ojmodel.enums.QuestionSubmitStatusEnum;
import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;
import com.pani.ojmodel.sandbox.JudgeInfo;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:10
 * @description 示例代码沙箱，仅为了搞清楚流程
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(executeCodeRequest.getInputList());
        executeCodeResponse.setMessage("测试---代码执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.FINISHED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
