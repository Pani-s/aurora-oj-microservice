package com.pani.auroraojjudgeservice.judge.codesandbox;


import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;

/**
 * @author Pani
 * @date Created in 2024/3/8 19:58
 * @description 代码沙箱 定义 接口 （被调用）
 */
public interface CodeSandbox {
    /**
     * 执行代码
     * @param executeCodeRequest 请求（包含语言、代码、判题输入）
     * @return 判题信息、输出结果
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
