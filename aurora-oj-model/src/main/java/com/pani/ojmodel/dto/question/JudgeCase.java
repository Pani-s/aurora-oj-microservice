package com.pani.ojmodel.dto.question;

import lombok.Data;

/**
 * @author Pani
 * @date Created in 2024/3/6 12:39
 * @description 判题用例（是判题的 不是展示(题目描述)的）
 */
@Data
public class JudgeCase {
    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;

}
