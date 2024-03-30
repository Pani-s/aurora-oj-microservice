package com.pani.auroraojjudgeservice.controller;

import com.pani.auroraojjudgeservice.judge.JudgeService;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.pani.ojcommon.annotation.AuthCheck;
import com.pani.ojcommon.common.BaseResponse;
import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.common.ResultUtils;
import com.pani.ojcommon.constant.UserConstant;
import com.pani.ojcommon.exception.BusinessException;
import com.pani.ojmodel.sandbox.SandboxTypeEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Pani
 * @date Created in 2024/3/19 13:13
 * @description
 */
@RestController
public class JudgeController {
    @Resource
    private JudgeService judgeService;

    /**
     * 修改沙箱类型（仅管理员）
     * @param type
     * @return
     */
    @PostMapping("/set/type")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    BaseResponse<Boolean> setCodeSandboxType(@RequestBody String type){
        if(!SandboxTypeEnum.isInEnum(type)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"非法的沙箱类型");
        }
//        if(!"remote".equals(type) && !"ai".equals(type) && !"example".equals(type)){
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
//        CodeSandboxFactory.setType(type);
        judgeService.setType(type);
        return ResultUtils.success(true);
    }

    /**
     * 修改沙箱类型（仅管理员）
     * @return
     */
    @GetMapping("/get/type")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    BaseResponse<String> getCodeSandboxType(){
        return ResultUtils.success(judgeService.getType());
    }

}
