package com.pani.ojcommon.constant;

/**
 * @author Pani
 * @date Created in 2024/3/25 20:48
 * @description
 */
public interface RedisConstant {
    /**
     * 题目页缓存
     */
    String CACHE_QUESTION_PAGE = "cache:question:page";
    /**
     * 题目信息缓存
     */
    String CACHE_QUESTION = "cache:question";

    long CACHE_NULL_TTL = 10;

    long CACHE_QUESTION_TTL = 30;
}
