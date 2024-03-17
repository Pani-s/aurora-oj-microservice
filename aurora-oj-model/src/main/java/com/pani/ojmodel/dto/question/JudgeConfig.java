package com.pani.ojmodel.dto.question;

import lombok.Data;

/**
 * @author Pani
 * @date Created in 2024/3/6 12:39
 * @description 题目配置 比如时间内存限制
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}
