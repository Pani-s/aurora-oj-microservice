package com.pani.auroraojjudgeservice.judge.strategy;


import com.pani.ojmodel.dto.question.JudgeCase;
import com.pani.ojmodel.dto.question.JudgeConfig;
import com.pani.ojmodel.enums.JudgeInfoMessageEnum;
import com.pani.ojmodel.sandbox.JudgeInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author Pani
 * @date Created in 2024/3/8 21:46
 * @description
 */
public class JavaLangJudgeStrategy implements JudgeStrategy {
    private final long JAVA_PROGRAM_TIME_COST = 10000L;
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        //根据语言不同。判题标准应该不一样 ---> 策略模式
        JudgeInfo judgeInfo = new JudgeInfo();

        //4.1 是否超时 超空间
        JudgeInfo exeJudgeInfo = judgeContext.getJudgeInfo();
        Long executeMemory = Optional.ofNullable(exeJudgeInfo.getMemory()).orElse(0L);
        Long executeTime = Optional.ofNullable(exeJudgeInfo.getTime()).orElse(0L);
        judgeInfo.setTime(executeTime);
        judgeInfo.setMemory(executeMemory);

        JudgeConfig judgeConfig = judgeContext.getJudgeConfig();
        Long timeLimit = judgeConfig.getTimeLimit();
        Long memoryLimit = judgeConfig.getMemoryLimit();
//                Long stackLimit = judgeConfig.getStackLimit();
        if (executeMemory!=0L && executeMemory > memoryLimit) {
            judgeInfo.setMessage(JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED.getValue());
            return judgeInfo;
        }
        // Java 程序本身需要额外执行 10 秒钟

        if (memoryLimit!=0L && (timeLimit - JAVA_PROGRAM_TIME_COST) > executeTime) {
            judgeInfo.setMessage(JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED.getValue());
            return judgeInfo;
        }


        //4.2.比较输出
        List<String> outputList = judgeContext.getOutputList();
        List<JudgeCase> judgeCaseList = judgeContext.getJudgeCaseList();
        //先比较大小
        if (outputList.size() != judgeCaseList.size()) {
            judgeInfo.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
            return judgeInfo;
        }
        //再一个一个比较
        for (int i = 0; i < outputList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!outputList.get(i).equals(judgeCase.getOutput())) {
                judgeInfo.setMessage(JudgeInfoMessageEnum.WRONG_ANSWER.getValue());
                return judgeInfo;
            }
        }
        //成功啦
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getValue());
        return judgeInfo;
    }
}
