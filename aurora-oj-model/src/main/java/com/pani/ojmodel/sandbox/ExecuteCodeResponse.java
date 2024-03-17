package com.pani.ojmodel.sandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/3/8 20:02
 * @description 代码沙箱 执行代码后返回的消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    /**
     * 执行产生的输出结果
     */
    List<String> outputList;
    /**
     * 代码沙箱的接口信息
     */
    private String message;

    /**
     * 执行状态 0正常结束 1编译错误 2运行错误
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

}
