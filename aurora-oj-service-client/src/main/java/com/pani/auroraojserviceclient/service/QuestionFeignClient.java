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
    @GetMapping("/get/id")
    Question getQuestionById(@RequestParam("questionId") long questionId);

    @GetMapping("/question_submit/get/id")
    QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId);

    @PostMapping("/question_submit/update")
    boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit);

    @PostMapping("/inc_acc")
    boolean incrAcNum(@RequestBody Long questionId);

    @GetMapping("/question_submit/fail")
    boolean setQuestionSubmitFailure(@RequestParam("questionId") long questionSubmitId);
}
