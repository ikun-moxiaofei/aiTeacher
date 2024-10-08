package com.mxf.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

import com.mxf.springbootinit.model.dto.question.QuestionQueryRequest;

import com.mxf.springbootinit.model.entity.Question;

import java.util.List;

/**
* @author JD
* @description 针对表【question(课程)】的数据库操作Service
* @createDate 2024-07-31 20:25:45
*/
public interface QuestionService extends IService<Question> {
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    List<Question> selectRandomQuestions(Long courseId, Integer state_0, Integer state_1);
}
