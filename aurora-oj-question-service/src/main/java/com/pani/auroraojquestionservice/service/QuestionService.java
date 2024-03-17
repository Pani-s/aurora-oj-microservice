package com.pani.auroraojquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pani.ojmodel.dto.question.QuestionEditRequest;
import com.pani.ojmodel.dto.question.QuestionQueryRequest;
import com.pani.ojmodel.dto.question.QuestionUpdateRequest;
import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Pani
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2024-03-06 12:30:26
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param question
     * @param add
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param question
     * @return
     */
    QuestionVO getQuestionVO(Question question);
//    QuestionVO getQuestionVO(Question question, HttpServletRequest request);
    //todo getQuestionVO ---HttpServletRequest

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getMyQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);


    /**
     * 获取题目
     * @param questionPage
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage);

    /**
     * 编辑题目的信息（普通用户自己）
     * @param questionEditRequest
     * @return 成功失败
     */
    Boolean editQuestion(QuestionEditRequest questionEditRequest, HttpServletRequest request);

    /**
     * 更新题目的信息（仅管理员）
     *
     * @param questionUpdateRequest
     * @return 成功失败
     */
    Boolean updateQuestion(QuestionUpdateRequest questionUpdateRequest);

    /**
     * 返回题目答案
     * @param id
     * @param request
     * @return
     */
    Question getQuestionAnswerById(long id, HttpServletRequest request);
}
