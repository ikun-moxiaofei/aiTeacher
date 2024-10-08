package com.mxf.springbootinit.service.impl;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.CourseMapper;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.User;
import com.mxf.springbootinit.model.vo.UserVO;
import com.mxf.springbootinit.service.CourseService;
import com.mxf.springbootinit.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 */
@Service
@Slf4j
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Override
    public QueryWrapper<Course> getQueryWrapper(CourseQueryRequest courseQueryRequest) {
        if (courseQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String courseDescription = courseQueryRequest.getCourseDescription();
        String courseName = courseQueryRequest.getCourseName();
        Long id = courseQueryRequest.getId();
        Long teacherId = courseQueryRequest.getTeacherId();
        String sortField = courseQueryRequest.getSortField();
        String sortOrder = courseQueryRequest.getSortOrder();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(courseName), "courseName", courseName);
        queryWrapper.eq(StringUtils.isNotBlank(courseDescription), "courseDescription", courseDescription);
        queryWrapper.eq(teacherId != null, "teacherId", teacherId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}
