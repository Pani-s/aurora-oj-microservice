package com.pani.ojmodel.vo;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pani.ojmodel.dto.question.JudgeConfig;
import com.pani.ojmodel.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author Pani
 * @date Created in 2024/3/6 12:51
 * @description
 */
@Data
public class QuestionVO {
    /**
     * id
     */
    private Long id;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 描述内容
     */
    private String content;

    /**
     * 标签列表（json 数组）
     */
    private List<String> tags;

    /**
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）时间内存限制
     */
    private JudgeConfig judgeConfig;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 当前用户是否通过
     */
    private Boolean isPass;

//    /**
//     * 创建时间
//     */
//    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date createTime;
//
//    /**
//     * 更新时间
//     */
//    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
//    private Date updateTime;
    
    /**
     * 创建题目人的信息
     */
    private UserVO userVO;

    private static final long serialVersionUID = 1L;
    
    /**
     * 包装类vo转对象
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        //转成json
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig judgeConfig1 = questionVO.getJudgeConfig();
        if (judgeConfig1 != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig1));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        questionVO.setTags(JSONUtil.toList(question.getTags(),String.class));
        questionVO.setJudgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class));
        //userVO还没查。转移到service层查
        return questionVO;
    }

}
