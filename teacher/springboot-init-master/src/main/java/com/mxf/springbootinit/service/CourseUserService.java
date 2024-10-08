package com.mxf.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserQueryRequest;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeQueryRequest;
import com.mxf.springbootinit.model.entity.CourseUser;
import com.mxf.springbootinit.model.entity.QuestionKnowledge;

import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author JD
* @description 针对表【course_user(选课对应表)】的数据库操作Service
* @createDate 2024-08-25 21:06:52
*/
public interface CourseUserService extends IService<CourseUser> {

    QueryWrapper<CourseUser> getQueryWrapper(CourseUserQueryRequest courseUserQueryRequest);


}
