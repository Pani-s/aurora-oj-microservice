package com.pani.ojmodel.sandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:00
 * @description 代码沙箱接收的 请求执行代码 参数
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteCodeRequest {
    /**
     * 判题输入
     */
    private List<String> inputList;
    /**
     * 使用的代码语言
     */
    private String language;
    /**
     * 将要执行的代码
     */
    private String code;
}
