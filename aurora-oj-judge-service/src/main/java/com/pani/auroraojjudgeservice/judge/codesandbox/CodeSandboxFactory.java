package com.pani.auroraojjudgeservice.judge.codesandbox;


import com.pani.auroraojjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:17
 * @description 静态工厂模式 - 指定代码沙箱的具体实现类
 * 如果确定代码沙箱示例不会出现线程安全问题、可复用，那么可以使用单例工厂模式
 */
public class CodeSandboxFactory {
    /**
     *
     * @param type 沙箱 类型
     * @return 实现类示例
     */
    public static CodeSandbox getInstance(String type){
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
