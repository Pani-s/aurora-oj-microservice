package com.pani.auroraojserviceclient.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/do")
    boolean doJudge(@RequestParam("questionSubmitId") long questionSubmitId);



}
