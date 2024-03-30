package com.pani.auroraojquestionservice.service;

import com.pani.ojmodel.entity.UserSubmit;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
