package com.pani.auroraojjudgeservice.judge.codesandbox.impl;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.pani.auroraojjudgeservice.judge.codesandbox.CodeSandbox;
import com.pani.ojcommon.common.ErrorCode;
import com.pani.ojcommon.exception.BusinessException;
import com.pani.ojmodel.sandbox.ExecuteCodeRequest;
import com.pani.ojmodel.sandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:10
 * @description 示例代码沙箱，仅为了搞清楚流程
 */
@Slf4j
@Component
public class ExampleCodeSandbox implements CodeSandbox {
    private final int TIMELIMIT_10 = 10000;

    @Value("${codeSandbox.remoteUrl}")
    private String remoteUrl;
    /**
     * 定义鉴权请求头和密钥
     */
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = SecureUtil.md5("kookv");

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("远程代码沙箱但是java");
        String url = remoteUrl + "/executeCode/java/";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        try (HttpResponse httpResponse = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json).timeout(TIMELIMIT_10)
                .execute()) {
            String responseStr = httpResponse.body();
            if (StringUtils.isBlank(responseStr)) {
                throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
            }
            return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
        } catch (Exception e) {
            log.info("远程代码沙箱java调用异常：" + e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }
}
