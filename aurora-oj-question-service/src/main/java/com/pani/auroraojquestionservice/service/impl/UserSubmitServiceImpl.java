package com.pani.auroraojquestionservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pani.auroraojquestionservice.service.QuestionSubmitService;
import com.pani.ojmodel.entity.UserSubmit;
import com.pani.auroraojquestionservice.service.UserSubmitService;
import com.pani.auroraojquestionservice.mapper.UserSubmitMapper;
import org.apache.catalina.mbeans.UserMBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Pani
* @description 针对表【user_submit(用户题目通过记录)】的数据库操作Service实现
* @createDate 2024-03-30 10:23:53
*/
@Service
public class UserSubmitServiceImpl extends ServiceImpl<UserSubmitMapper, UserSubmit>
    implements UserSubmitService{

    @Override
    public boolean updateUserSubmitRecord(long questionId,long userId) {
        QueryWrapper<UserSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        queryWrapper.eq("questionId",questionId);
        long count = this.count(queryWrapper);
        if(count > 0){
            return true;
        }
        UserSubmit userSubmit = new UserSubmit();
        userSubmit.setUserId(userId);
        userSubmit.setQuestionId(questionId);
        return this.save(userSubmit);
    }
}




