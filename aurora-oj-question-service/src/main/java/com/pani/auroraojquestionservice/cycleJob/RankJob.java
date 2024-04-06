package com.pani.auroraojquestionservice.cycleJob;

import com.pani.auroraojquestionservice.manager.CacheClient;
import com.pani.auroraojquestionservice.service.UserSubmitService;
import com.pani.ojcommon.constant.RedisConstant;
import com.pani.ojmodel.vo.Rank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Pani
 * @date Created in 2024/3/31 9:09
 * @description
 */
@Component
@Slf4j
public class RankJob {
    @Resource
    private UserSubmitService userSubmitService;

    @Resource
    private CacheClient cacheClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 每日零点 触发 更新排行榜 保存到redis
     * 排行榜的结构：用户id 用户名 用户头像 新通过题目
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateRank() {
        LocalDateTime now = LocalDateTime.now();
        String nowStr = now.format(formatter);
        // 获取当前日期，并设置为昨天
        LocalDateTime yesterday = now.minusDays(3);
        String yesterdayStr = yesterday.format(formatter);
        // 从题目通过记录表中统计每个用户新通过的题号数量
        List<Rank> userRankToday = userSubmitService.updateUserRankTodayNewPass(yesterdayStr, nowStr, 3);
        cacheClient.set(RedisConstant.CACHE_RANK_NEW_PASS, userRankToday, 1L, TimeUnit.DAYS);
    }
}
