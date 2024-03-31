package com.pani.auroraojquestionservice.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pani.auroraojquestionservice.manager.RedisLimiterManager;
import com.pani.auroraojquestionservice.service.QuestionService;
import com.pani.auroraojquestionservice.service.QuestionSubmitService;
import com.pani.auroraojquestionservice.service.UserSubmitService;
import com.pani.auroraojserviceclient.service.UserFeignClient;
import com.pani.ojcommon.annotation.AuthCheck;
import com.pani.ojcommon.common.BaseResponse;
import com.pani.ojcommon.common.DeleteRequest;
import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.common.ResultUtils;
import com.pani.ojcommon.constant.RedisConstant;
import com.pani.ojcommon.constant.UserConstant;
import com.pani.ojcommon.exception.BusinessException;
import com.pani.ojcommon.exception.ThrowUtils;
import com.pani.ojmodel.dto.question.*;
import com.pani.ojmodel.dto.questionsubmit.QuestionSubmitAddRequest;
import com.pani.ojmodel.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.pani.ojmodel.entity.Question;
import com.pani.ojmodel.entity.QuestionSubmit;
import com.pani.ojmodel.entity.User;
import com.pani.ojmodel.vo.QuestionSubmitVO;
import com.pani.ojmodel.vo.QuestionVO;
import com.pani.ojmodel.vo.Rank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 题目接口
 *
 * @author pani
 *
 * getmapping遇到很多坑，还是回来用postmapping吧
 * 
 */
@RestController
@RequestMapping("/")
@Slf4j
public class QuestionController {

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Resource
    private QuestionService questionService;

    @Resource
    private UserSubmitService userSubmitService;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private QuestionSubmitService questionSubmitService;

    //准备都用Hutool了
//    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestion(@RequestBody QuestionAddRequest questionAddRequest, HttpServletRequest request) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        List<String> tags = questionAddRequest.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        List<JudgeCase> judgeCase = questionAddRequest.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfig judgeConfig = questionAddRequest.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        questionService.validQuestion(question, true);
        User loginUser = userFeignClient.getLoginUser(request);
        question.setUserId(loginUser.getId());
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionId = question.getId();
        return ResultUtils.success(newQuestionId);
    }

    /**
     * 删除 （仅本人或管理员）
     *
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userFeignClient.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        Question oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId()) && !userFeignClient.isAdmin(user)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = questionService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 前端用的是update
     * 更新（仅管理员）
     *
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionUpdateRequest questionUpdateRequest) {
        if (questionUpdateRequest == null || questionUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = questionService.updateQuestion(questionUpdateRequest);
        return ResultUtils.success(b);
    }

    /**
     * 根据 id 获取 题目内容（只能管理员或者本人）
     *
     */
    @GetMapping("/get")
    public BaseResponse<Question> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        // 不是本人或管理员，不能直接获取所有信息
        if (!question.getUserId().equals(loginUser.getId()) && !userFeignClient.isAdmin(loginUser)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return ResultUtils.success(question);
    }


    /**
     * 根据 id 获取 题目VO（脱敏后）
     *
     */
    @GetMapping("/get/vo")
    public BaseResponse<QuestionVO> getQuestionVOById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionVOById(id));
//        Question question = questionService.getById(id);
//        if (question == null) {
//            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
//        }
//        return ResultUtils.success(questionService.getQuestionVO(question));

    }

    /**
     * 根据 id 获取 答案，前提是这个用户做过
     *
     */
    @GetMapping("/get/answer")
    public BaseResponse<Question> getQuestionAnswerById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return ResultUtils.success(questionService.getQuestionAnswerById(id,request));
    }

    /**
     * 分页获取列表（封装类）
     *QuestionVO中的UserVO没有填充
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
            HttpServletRequest request) {
        /*
        其实题目列表缓存。。。是否需要说题目数更新了之后就删除缓存呢？不过题目信息的更新需要删除缓存
         */
        ThrowUtils.throwIf(questionQueryRequest == null,ErrorCode.PARAMS_ERROR);
        //不支持以答案和内容查找
        questionQueryRequest.setAnswer(null);
        questionQueryRequest.setContent(null);
        return ResultUtils.success(questionService.listQuestionVOByPage(questionQueryRequest,request));
    }

    /**
     * 分页获取当前用户创建的问题列表
     *
     */
    @PostMapping("/my/list/page/vo")
    public BaseResponse<Page<QuestionVO>> listMyQuestionVOByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
            HttpServletRequest request) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        questionQueryRequest.setUserId(loginUser.getId());
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionService.getMyQuestionVOPage(questionPage, request));
    }

    /**
     * 分页获取题目列表（仅管理员）
     *
     */
    @PostMapping("/list/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest,
                                                           HttpServletRequest request) {
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        Page<Question> questionPage = questionService.page(new Page<>(current, size),
                questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage);
    }





    /**
     * 编辑题目的信息（用户）
     *
     */
    @PostMapping("/edit")
    public BaseResponse<Boolean> editQuestion(@RequestBody QuestionEditRequest questionEditRequest, HttpServletRequest request) {
        if (questionEditRequest == null || questionEditRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = questionService.editQuestion(questionEditRequest, request);
        return ResultUtils.success(b);
    }

    // endregion
    //region 题目提交
    /**
     * 提交题目
     * @return id
     */
    @PostMapping("/submit/do")
    public BaseResponse<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
                                               HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getQuestionId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能操作
        final User loginUser = userFeignClient.getLoginUser(request);
        //判定限流
        redisLimiterManager.doRateLimit(RedisConstant.QUESTION_SUBMIT_LIMIT + loginUser.getId());
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddRequest, loginUser);
        return ResultUtils.success(questionSubmitId);
    }

    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     * 该题目的提交列表---可以看代码，目前不能看提交用户名，我觉得还是匿名吧
     */
    @PostMapping("/submit/list/page")
    public BaseResponse<Page<QuestionSubmitVO>> listSubmitQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        final User loginUser = userFeignClient.getLoginUser(request);
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitService.getQuestionSubmitVOPage(questionSubmitPage, loginUser));
    }

    /**
     * 获取本人提交的题目
     */
    @PostMapping("/submit/list/my/page")
    public BaseResponse<Page<QuestionSubmit>> listMySubmitQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                         HttpServletRequest request) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userFeignClient.getLoginUser(request);
        questionSubmitQueryRequest.setUserId(loginUser.getId());

        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        // 返回脱敏信息
        return ResultUtils.success(questionSubmitPage);
    }


    /**
     * 所有人的所有题目提交记录，按照时间排序(【【公屏】】，所以只能看到【提交用户id（甚至匿名） 语言)
     */
    @PostMapping("/submit/list/all/page")
    public BaseResponse<Page<QuestionSubmit>> listAllSubmitQuestionByPage(@RequestBody QuestionSubmitQueryRequest questionSubmitQueryRequest,
                                                                          HttpServletRequest request) {
        if (questionSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = questionSubmitQueryRequest.getCurrent();
        long size = questionSubmitQueryRequest.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmit> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryRequest));
        //提交代码不给看了
        questionSubmitPage.getRecords().forEach((o) -> o.setCode(null));
        return ResultUtils.success(questionSubmitPage);
    }

    /**
     * 重试修改失败的提交
     */
    @PostMapping("/submit/retry")
    public BaseResponse<Boolean> retryMyErrorSubmit(@RequestBody DeleteRequest deleteRequest,
                                                  HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = questionSubmitService.retryMyErrorSubmit(deleteRequest.getId(), request);
        return ResultUtils.success(b);
    }

    /**
     * 删除修改失败的提交
     */
    @PostMapping("/submit/delete")
    public BaseResponse<Boolean> delMyErrorSubmit(@RequestBody DeleteRequest deleteRequest,
                                                                          HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        questionSubmitService.checkErrorQuestion(deleteRequest.getId(),request);
        //删除
        boolean b = questionSubmitService.removeById(deleteRequest.getId());
        if(!b){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"删除失败！");
        }
        return ResultUtils.success(b);
    }
    //endregion
    //region rank
    /**
     * 获取每日排行榜 -- 新通过数
     */
    @GetMapping("/rank/daily/new")
    public BaseResponse<List<Rank>> getDailyRankNewPass() {
        return ResultUtils.success(userSubmitService.getDailyRankNewPass());
    }
    //endregion
}
