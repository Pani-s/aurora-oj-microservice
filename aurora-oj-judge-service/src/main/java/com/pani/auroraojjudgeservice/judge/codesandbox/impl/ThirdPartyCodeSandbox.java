package com.pani.auroraojjudgeservice.judge.codesandbox.impl;


import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandbox;
import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:10
 * @description 引入的第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return null;
    }
}
