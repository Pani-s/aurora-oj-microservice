package com.pani.auroraojquestionservice.service;

import com.pani.ojmodel.entity.UserSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pani.ojmodel.vo.Rank;

import java.util.List;
import java.util.Map;

/**
* @author Pani
* @description 针对表【user_submit(用户题目通过记录)】的数据库操作Service
* @createDate 2024-03-30 10:23:53
*/
public interface UserSubmitService extends IService<UserSubmit> {

    /**
     * 更新 - 如果用户已经通过了就不做 用户没通过就增加记录
     */
    boolean updateUserSubmitRecord(long questionId,long userId);

    /**
     *更新排行榜，昨天(或者特定时间)到今天前number名（新通过题目数）
     */
    List<Rank> updateUserRankTodayNewPass(String yesterday, String now, int number);

    /**
     * 查询该userID下 一群questionIds是否存在
     */
    Map<Long, Map<String, Long>> checkExistForQuestionIdsMapper(Long userId, List<Long> questionIds);

    List<Rank> getDailyRankNewPass();
}
