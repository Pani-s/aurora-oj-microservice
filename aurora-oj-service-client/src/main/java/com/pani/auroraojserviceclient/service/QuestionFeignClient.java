package com.pani.auroraojserviceclient.service;

import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Pani
 * @description 针对表【question(题目)】的数据库操作Service
 * @createDate 2024-03-06 12:30:26
 */
@FeignClient(name = "aurora-oj-question-service", path = "/api/question/inner")
public interface QuestionFeignClient {
    /**
     * 获取question
     * @param questionId
     * @return
     */
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    /**
     * 获取 questionSubmitId
     * @param questionSubmitId
     * @return
     */
    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId);

    /**
     * 更新 questionSubmitId
     * @param questionSubmit
     * @return
     */
    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    /**
     * 【暂时没用】
     * 增加 题目通过数
     * @param questionId
     * @return
     */
    @PostMapping("/inc_acc")
    boolean incrAcNum(@RequestBody Long questionId);

    /**
     * 将题目提交信息设置为失败~
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/question_submit/fail")
    boolean setQuestionSubmitFailure(@RequestBody long questionSubmitId);

    /**【暂时没用】
     * 更新 - 如果用户已经通过了就不做 用户没通过就增加记录
     * @param questionSubmitId
     * @return
     */
    @PostMapping("/user_submit/update")
    boolean updateUserSubmitRecord(@RequestBody long questionSubmitId);
}
