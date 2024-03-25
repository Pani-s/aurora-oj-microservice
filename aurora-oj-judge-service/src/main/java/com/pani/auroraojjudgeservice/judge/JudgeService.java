package com.pani.auroraojjudgeservice.judge;
/**
 * @author Pani
 * @date Created in 2024/3/8 20:41
 * @description 判题服务
 */
public interface JudgeService {
    int RUN_SUCCESS = 0;
    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    boolean doJudge(long questionSubmitId);

    void setType(String type);
}
