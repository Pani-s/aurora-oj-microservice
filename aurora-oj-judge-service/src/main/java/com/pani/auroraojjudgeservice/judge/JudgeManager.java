package com.pani.auroraojjudgeservice.judge;

import com.pani.auroraojjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.pani.auroraojjudgeservice.judge.strategy.JavaLangJudgeStrategy;
import com.pani.auroraojjudgeservice.judge.strategy.JudgeContext;
import com.pani.auroraojjudgeservice.judge.strategy.JudgeStrategy;
import com.pani.ojmodel.enums.QuestionSubmitLanguageEnum;
import com.pani.ojmodel.sandbox.JudgeInfo;
import org.springframework.stereotype.Component;

/**
 * @author Pani
 * @date Created in 2024/3/8 21:43
 * @description 判题管理,简化调用
 */
@Component
public class JudgeManager {
    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        String language = judgeContext.getLanguage();
        JudgeStrategy judgeStrategy;
        if (QuestionSubmitLanguageEnum.JAVA.getValue().equals(language)) {
            judgeStrategy = new JavaLangJudgeStrategy();
        }else{
            judgeStrategy = new DefaultJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
