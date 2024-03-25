package com.pani.auroraojquestionservice.controller.inner;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pani.auroraojquestionservice.service.QuestionService;
import com.pani.auroraojquestionservice.service.QuestionSubmitService;
import com.pani.auroraojserviceclient.service.QuestionFeignClient;
import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.entity.QuestionSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/17 11:46
 * @description
 */
@Slf4j
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        log.info("controller --- getQuestionSubmitById：{}",questionSubmitId);
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

    @Override
    public boolean incrAcNum(Long questionId) {
        return questionService.incrAcNum(questionId);
    }

    @Override
    public boolean setQuestionSubmitFailure(long questionSubmitId) {
        questionSubmitService.setQuestionSubmitFailure(questionSubmitId);
        return false;
    }

}
