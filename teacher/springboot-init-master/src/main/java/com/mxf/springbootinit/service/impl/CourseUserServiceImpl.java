package com.mxf.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.CourseUserMapper;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserQueryRequest;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeQueryRequest;
import com.mxf.springbootinit.model.entity.CourseUser;


import com.mxf.springbootinit.model.entity.QuestionKnowledge;
import com.mxf.springbootinit.service.CourseUserService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.springframework.stereotype.Service;

import javax.swing.*;

/**
* @author JD
* @description 针对表【course_user(选课对应表)】的数据库操作Service实现
* @createDate 2024-08-25 21:06:52
*/
@Service
public class CourseUserServiceImpl extends ServiceImpl<CourseUserMapper, CourseUser>
    implements CourseUserService {

    public QueryWrapper<CourseUser> getQueryWrapper(CourseUserQueryRequest courseUserQueryRequest){
        if (courseUserQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = 1L;
        if (courseUserQueryRequest.getId() != null) {
            id = courseUserQueryRequest.getId();
        }else {
            id = null;
        }
        Long courseId = 1L;
        if (courseUserQueryRequest.getCourseId() != null) {
            courseId = courseUserQueryRequest.getCourseId();
        }else {
            courseId = null;
        }
        Long userId = 1L;
        if (courseUserQueryRequest.getUserId() != null) {
            userId = courseUserQueryRequest.getUserId();
        }else {
            userId = null;
        }

        int current = courseUserQueryRequest.getCurrent();
        int pageSize = courseUserQueryRequest.getPageSize();
        String sortField = courseUserQueryRequest.getSortField();
        String sortOrder = courseUserQueryRequest.getSortOrder();

        QueryWrapper<CourseUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(courseId != null, "courseId", courseId);
        queryWrapper.eq(userId != null, "userId", userId);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }



}




