package com.pani.auroraojquestionservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pani.ojmodel.entity.UserSubmit;
import com.pani.ojmodel.vo.Rank;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Pani
 * @description 针对表【user_submit(用户题目通过记录)】的数据库操作Mapper
 * @createDate 2024-03-30 10:23:53
 * @Entity com.pani.ojmodel.entity.UserSubmit
 */
public interface UserSubmitMapper extends BaseMapper<UserSubmit> {

    /**
     * 查询该userID下 一群questionIds是否存在
     * mapKey 毁了我
     *
     * @param userId
     * @param questionIds
     * @return
     */
    @MapKey("questionId")
    Map<Long, Map<String, Long>> checkExistForQuestionIds(@Param("userId") Long userId,
                                                          @Param("questionIds") List<Long> questionIds);


    /**
     * 昨天到今天，统计用户与count
     *
     * @return
     */
    List<Rank> userCountDuringTime(@Param("yesterday") String yesterday, @Param("now") String now,
                                   @Param("num") int number);

}




