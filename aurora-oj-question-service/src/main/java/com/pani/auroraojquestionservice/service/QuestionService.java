package com.pani.auroraojquestionservice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pani.ojmodel.dto.question.QuestionEditRequest;
import com.pani.ojmodel.dto.question.QuestionQueryRequest;
import com.pani.ojmodel.dto.question.QuestionUpdateRequest;
import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.vo.QuestionVO;
import org.springframework.web.bind.annotation.RequestBody;

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
     * 分页获取列表（封装类）
     * QuestionVO中的UserVO没有填充
     */
    Page<QuestionVO> listQuestionVOByPage(QuestionQueryRequest questionQueryRequest,
                                          HttpServletRequest request);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);

    /**
     * 获取题目封装 要UserVO
     *
     * @param question
     * @return
     */
    QuestionVO getQuestionVO(Question question);
//    QuestionVO getQuestionVO(Question question, HttpServletRequest request);
    //todo getQuestionVO ---HttpServletRequest

    /**
     * 获取题目封装，【不要userVO】
     * @param id
     * @return
     */
    QuestionVO getQuestionVOById(long id);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getMyQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);


    /**
     * 获取题目 但是不装载userVO
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

    /**
     * 题目通过数 + 1
     * @param questionId
     * @return
     */
    boolean incrAcNum(Long questionId);
}
