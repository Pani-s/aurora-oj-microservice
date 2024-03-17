package com.pani.auroraojjudgeservice.judge;

import cn.hutool.json.JSONUtil;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandbox;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandboxProxy;
import com.pani.auroraojjudgeservice.judge.strategy.JudgeContext;
import com.pani.auroraojserviceclient.service.QuestionFeignClient;
import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.exception.BusinessException;
import com.pani.ojmodel.dto.question.JudgeCase;
import com.pani.ojmodel.dto.question.JudgeConfig;
import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.entity.QuestionSubmit;
import com.pani.ojmodel.enums.QuestionSubmitStatusEnum;
import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;
import com.pani.ojmodel.sandbox.JudgeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:42
 * @description
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Value("${codeSandbox.type:example}")
    private String type;
    @Resource
    private QuestionFeignClient questionFeignClient;
    @Resource
    private JudgeManager judgeManager;


    /**
     * 题外话：因为代码量太多，本来想把前面抽出成单独的方法，但question的judge case需要获得，request需要获得
     * 最后还是先放回一块了
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //1 传入题目的提交 id，在数据库中获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmit questionSubmit = questionFeignClient.getQuestionSubmitById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在！");
        }
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        Long questionId = questionSubmit.getQuestionId();
        Integer status = questionSubmit.getStatus();

        //如果题目提交状态不为等待中，就不用重复执行了
        if (!status.equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "正在判题中，请勿重复提交！");
        }

        //查找题目信息
        Question question = questionFeignClient.getQuestionById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在！");
        }
        String judgeCase = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCase, JudgeCase.class);
        //stream 真好用，爱来自拆那，下次还用stream
        List<String> inputList = judgeCaseList.stream().
                map(JudgeCase::getInput).collect(Collectors.toList());


        //2 更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean b = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新题目提交状态失败！");
        }

        //3 调用沙箱，获取到执行结果
        ExecuteCodeRequest request = ExecuteCodeRequest.builder().
                code(code).
                language(language).
                inputList(inputList).
                build();
        CodeSandbox codeSandbox = CodeSandboxFactory.getInstance(type);
        ExecuteCodeResponse executeCodeResponse = new CodeSandboxProxy(codeSandbox).
                executeCode(request);


        //4 根据沙箱的执行结果，设置题目的判题状态和信息
        Integer resStatus = executeCodeResponse.getStatus();
        if(!resStatus.equals(0)){
            //说明没有正常退出
            questionSubmit = new QuestionSubmit();
            questionSubmit.setId(questionSubmitId);
            questionSubmit.setStatus(QuestionSubmitStatusEnum.FAILED.getValue());
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(executeCodeResponse.getJudgeInfo()));
            b = questionFeignClient.updateQuestionSubmitById(questionSubmit);
            if (!b) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
            }
            QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
            return questionSubmitResult;
        }
        //根据语言不同。判题标准应该不一样 ---> 策略模式
//        JudgeInfo judgeInfo = new JudgeInfo();

        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setJudgeConfig(judgeConfig);
        judgeContext.setLanguage(questionSubmit.getLanguage());
        //策略模式
        JudgeInfo judgeInfoRes = judgeManager.doJudge(judgeContext);

        // 5）修改数据库中的判题结果
        questionSubmit = new QuestionSubmit();
        questionSubmit.setId(questionSubmitId);
        questionSubmit.setStatus(QuestionSubmitStatusEnum.FINISHED.getValue());
        questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoRes));
        b = questionFeignClient.updateQuestionSubmitById(questionSubmit);
        if (!b) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目提交状态更新错误");
        }
        QuestionSubmit questionSubmitResult = questionFeignClient.getQuestionSubmitById(questionId);
        //通过数加一
        if(!questionFeignClient.incrAcNum(questionId)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目通过数信息更新错误");
        }
        return questionSubmitResult;
    }

}
