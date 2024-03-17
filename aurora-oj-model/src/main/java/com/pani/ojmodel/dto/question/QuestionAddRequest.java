package com.pani.ojmodel.dto.question;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建请求
 *
 * @author pani
 */
@Data
public class QuestionAddRequest implements Serializable {


    /**
     * 题目标题
     */
    private String title;

    /**
     * 描述内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目答案（代码）
     */
    private String answer;

    /**
     * 判题用例（json 数组）
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置（json 对象）时间内存限制
     */
    private JudgeConfig judgeConfig;


    private static final long serialVersionUID = 1L;
}