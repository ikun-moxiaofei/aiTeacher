package com.mxf.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxf.springbootinit.model.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author JD
* @description 针对表【question(课程)】的数据库操作Mapper
* @createDate 2024-07-31 20:25:45
* @Entity generator.domain.Question
*/
public interface QuestionMapper extends BaseMapper<Question> {

    // 根据课程ID和状态随机选择指定数量的题目
    @Select("SELECT * FROM question WHERE courseId = #{courseId} AND state = #{state} ORDER BY RAND() LIMIT #{limit}")
    List<Question> selectRandomByState(@Param("courseId") Long courseId, @Param("state") Integer state, @Param("limit") int limit);

}




