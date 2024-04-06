package com.pani.auroraojjudgeservice.judge.strategy;


import com.pani.ojmodel.sandbox.JudgeInfo;

/**
 * @author Pani
 * @date Created in 2024/3/8 21:06
 * @description
 */
public interface JudgeStrategy {
    int DIVIDE_TO_KB = 1024;
    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);

}
