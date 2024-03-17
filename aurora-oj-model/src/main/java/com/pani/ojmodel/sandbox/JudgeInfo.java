package com.pani.ojmodel.sandbox;

import lombok.Data;

/**
 * @author Pani
 * @date Created in 2024/3/6 16:50
 * @description 判题信息
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间（KB）
     */
    private Long time;

    /**
     * 执行的详细信息
     */
    private String details;
}
