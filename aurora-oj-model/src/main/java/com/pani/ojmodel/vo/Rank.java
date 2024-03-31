package com.pani.ojmodel.vo;

import lombok.Data;

/**
 * @author Pani
 * @date Created in 2024/3/31 10:09
 * @description 排行榜Rank
 */
@Data
public class Rank {
    /**
     * 题目通过数
     */
    private Long count;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;
}
