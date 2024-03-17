package com.pani.ojmodel.sandbox;

import lombok.Data;

/**
 * @author Pani
 * @date Created in 2024/3/9 15:07
 * @description
 */
@Data
public class ExecuteMessage {
    /**
     * 退出代码
     */
    private int exitValue = -1;
    /**
     * 正确执行信息
     */
    private String message;
    /**
     * 错误执行信息
     */
    private String errorMessage;
    /**
     * 执行时间
     */
    private Long time;
    /**
     * 执行内存消耗
     */
    private Long memory;
}
