package com.pani.auroraojserviceclient.service;


import com.pani.ojmodel.dto.questionsubmit.QuestionDebugRequest;
import com.pani.ojmodel.vo.QuestionDebugResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:41
 * @description 判题服务
 */
@FeignClient(name = "aurora-oj-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * 判题
     *
     * @param questionSubmitId
     */
    @PostMapping("/do")
    boolean doJudge(@RequestBody long questionSubmitId);

    /**
     * 测试运行
     * @param questionDebugRequest
     * @return
     */
    @PostMapping("/debug")
    QuestionDebugResponse doDebug(@RequestBody QuestionDebugRequest questionDebugRequest);



}
