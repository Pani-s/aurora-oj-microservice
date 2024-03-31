package com.pani.auroraojquestionservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pani.auroraojquestionservice.manager.CacheClient;
import com.pani.auroraojquestionservice.mapper.UserSubmitMapper;
import com.pani.auroraojquestionservice.service.UserSubmitService;
import com.pani.auroraojserviceclient.service.UserFeignClient;
import com.pani.ojcommon.constant.RedisConstant;
import com.pani.ojmodel.entity.User;
import com.pani.ojmodel.entity.UserSubmit;
import com.pani.ojmodel.vo.Rank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Pani
 * @description 针对表【user_submit(用户题目通过记录)】的数据库操作Service实现
 * @createDate 2024-03-30 10:23:53
 */
@Service
public class UserSubmitServiceImpl extends ServiceImpl<UserSubmitMapper, UserSubmit>
        implements UserSubmitService {
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private UserSubmitMapper userSubmitMapper;
    @Resource
    private CacheClient cacheClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public boolean updateUserSubmitRecord(long questionId, long userId) {
        //        QueryWrapper<UserSubmit> queryWrapper = new QueryWrapper<>();
        //        queryWrapper.eq("userId",userId);
        //        queryWrapper.eq("questionId",questionId);
        //        long count = this.count(queryWrapper);
        //        if(count > 0){
        //            return true;
        //        }
        /*
        这里就不提前判断了
        save方法首先会先判断该数据是否已经存在于数据库中，如果存在则更新数据，如果不存在则插入数据
         */
        UserSubmit userSubmit = new UserSubmit();
        userSubmit.setUserId(userId);
        userSubmit.setQuestionId(questionId);
        return this.save(userSubmit);
    }

    @Override
    public List<Rank> updateUserRankTodayNewPass(String yesterday, String now, int number) {
        List<Rank> userRankToday = userSubmitMapper.userCountDuringTime(yesterday, now, number);
        if (userRankToday.isEmpty()) {
            return userRankToday;
        }

        ArrayList<Long> userIds = new ArrayList<>();

        for (Rank rank : userRankToday) {
            Long userId = rank.getUserId();
            userIds.add(userId);
        }
        //批查询
        Map<Long, List<User>> userIdUserListMap = userFeignClient.listByIds(userIds).stream()
                .collect(Collectors.groupingBy(User::getId));
        for (Rank r : userRankToday) {
            User user = userIdUserListMap.get(r.getUserId()).get(0);
            r.setUserName(user.getUserName());
            r.setUserAvatar(user.getUserAvatar());
        }
        return userRankToday;
    }

    @Override
    public Map<Long, Map<String, Long>> checkExistForQuestionIdsMapper(Long userId, List<Long> questionIds) {
        return userSubmitMapper.checkExistForQuestionIds(userId, questionIds);
    }

    @Override
    public List<Rank> getDailyRankNewPass() {
        List<Rank> list = null;
        list = cacheClient.getList(RedisConstant.CACHE_RANK_NEW_PASS, Rank.class);
        //没有的话返回一个空数组
        if (list == null) {
            //            list = new ArrayList<>();
            LocalDate now = LocalDate.now();
            String nowStr = now.format(formatter);
            // 获取当前日期，并设置为昨天
            LocalDate yesterday = now.minusDays(1);
            String yesterdayStr = yesterday.format(formatter);
            List<Rank> ranks = this.updateUserRankTodayNewPass(yesterdayStr + " 00:00:00", nowStr + " 00:00:00", 3);
            cacheClient.set(RedisConstant.CACHE_RANK_NEW_PASS, ranks, 12L, TimeUnit.HOURS);
            return ranks;
        }
        return list;
    }
}




