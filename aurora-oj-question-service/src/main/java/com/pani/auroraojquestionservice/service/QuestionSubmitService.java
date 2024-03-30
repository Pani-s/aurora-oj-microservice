package com.pani.auroraojquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pani.ojmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.pani.ojmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.pani.ojmodel.entity.QuestionSubmit;
import com.pani.ojmodel.entity.User;
import com.pani.ojmodel.vo.QuestionSubmitVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pani
 * @description 针对表【question_submit(题目提交)】的数据库操作Service
 * @createDate 2024-03-06 12:30:40
 */
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);


    /**
     * 设置提交状态为FAIL
     *
     * @param questionSubmitId
     * @return
     */
    boolean setQuestionSubmitFailure(long questionSubmitId);

    /**
     * 检查 error状态 题目 和 用户是否匹配
     *
     * @param questionSubmitId
     * @param request
     */
    void checkErrorQuestion(long questionSubmitId, HttpServletRequest request);

    /**
     * 重试ERROR状态的题目提交
     * @param questionSubmitId
     * @param request
     * @return
     */
    boolean retryMyErrorSubmit(long questionSubmitId, HttpServletRequest request);
}
