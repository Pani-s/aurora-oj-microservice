package com.pani.ojmodel.dto.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交题目 的 创建请求
 *
 * @author pani
 */
@Data
public class QuestionDebugRequest implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 输入用例
     */
    private String input;

    /**
     * 题目 id
     */
    private Long questionId;


    private static final long serialVersionUID = 1L;
}