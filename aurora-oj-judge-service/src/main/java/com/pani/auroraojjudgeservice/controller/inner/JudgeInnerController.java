package com.pani.auroraojjudgeservice.controller.inner;

import com.pani.auroraojjudgeservice.judge.JudgeService;
import com.pani.auroraojserviceclient.service.JudgeFeignClient;
import com.pani.ojmodel.dto.questionsubmit.QuestionDebugRequest;
import com.pani.ojmodel.vo.QuestionDebugResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/17 11:56
 * @description
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {
    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     *
     * @param questionSubmitId
     */
    @Override
    public boolean doJudge(long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }

    @Override
    public QuestionDebugResponse doDebug(QuestionDebugRequest questionDebugRequest) {
        return judgeService.doDebug(questionDebugRequest);
    }


}
