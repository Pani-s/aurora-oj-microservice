package com.pani.auroraojjudgeservice.judge.codesandbox;


import com.pani.auroraojjudgeservice.judge.codesandbox.impl.AiCodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.impl.ExampleCodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.impl.RemoteCodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:17
 * @description 静态工厂模式 - 指定代码沙箱的具体实现类
 * 如果确定代码沙箱示例不会出现线程安全问题、可复用，那么可以使用单例工厂模式
 * 屈服了，加上注解、、
 */
@Slf4j
@Component
public class CodeSandboxFactory {

    /**
     * 静态字段不可以
     */
    //    @Value("${codeSandbox.type:example}")
    //    private static String type;

    //    private static final HashMap<String,CodeSandbox> MAP;

    @Resource
    private RemoteCodeSandbox remoteCodeSandbox;
    @Resource
    private ThirdPartyCodeSandbox thirdPartyCodeSandbox;
    @Resource
    private ExampleCodeSandbox exampleCodeSandbox;
    @Resource
    private AiCodeSandbox aiCodeSandbox;

    //    static{
    //        MAP = new HashMap<>();
    //        MAP.put("remote",new RemoteCodeSandbox());
    //        MAP.put("thirdParty",new ThirdPartyCodeSandbox());
    //        MAP.put("example",new ExampleCodeSandbox());
    //        MAP.put("ai",new AiCodeSandbox());
    //    }

    public CodeSandbox getInstanceWithType(String type) {
        switch (type) {
            case "ai":
                return aiCodeSandbox;
            case "example":
                return exampleCodeSandbox;
            case "remote":
                return remoteCodeSandbox;
            case "thirdParty":
                return thirdPartyCodeSandbox;
            default:
                return exampleCodeSandbox;
        }
    }

    //    public static void setType(String type) {
    //        log.info("sandbox 类型发生变换:{}",type);
    //        CodeSandboxFactory.type = type;
    //    }

    //    public static CodeSandbox getInstanceWithType(String type){
    //        if(MAP.containsKey(type)){
    //            return MAP.get(type);
    //        }else{
    //            return MAP.get("example");
    //        }
    //        //        switch (type){
    //        //            case "example":
    //        //                return new ExampleCodeSandbox();
    //        //            case "remote":
    //        //                return new RemoteCodeSandbox();
    //        //            case "thirdParty":
    //        //                return new ThirdPartyCodeSandbox();
    //        //            default:
    //        //                return new ExampleCodeSandbox();
    //        //        }
    //    }

    //    /**
    //     * 实现灵活
    //     * @return 实现类示例
    //     */
    //    public static CodeSandbox getInstance(){
    //        System.err.println(type);
    //        if(map.containsKey(type)){
    //            return map.get(type);
    //        }else{
    //            return map.get("example");
    //        }
    //        //        switch (type){
    //        //            case "example":
    //        //                return new ExampleCodeSandbox();
    //        //            case "remote":
    //        //                return new RemoteCodeSandbox();
    //        //            case "thirdParty":
    //        //                return new ThirdPartyCodeSandbox();
    //        //            default:
    //        //                return new ExampleCodeSandbox();
    //        //        }
    //    }
}
