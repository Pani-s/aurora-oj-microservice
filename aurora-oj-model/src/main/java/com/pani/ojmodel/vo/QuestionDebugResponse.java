package com.pani.ojmodel.vo;

import com.pani.ojmodel.sandbox.JudgeInfo;
import lombok.Data;

import java.io.Serializable;

/**
 * 提交题目 的 创建请求
 *
 * @author pani
 */
@Data
public class QuestionDebugResponse implements Serializable {
    /**
     * 运行成功or失败
     */
    private Boolean isSuccess;

    /**
     * 判题信息（json 对象）比如失败原因
     */
    private JudgeInfo judgeInfo;

    /**
     * 程序输出
     */
    private String output;


    private static final long serialVersionUID = 1L;
}