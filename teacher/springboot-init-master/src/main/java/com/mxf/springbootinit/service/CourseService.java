package com.mxf.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.entity.Course;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 */
public interface CourseService extends IService<Course> {

    /**
     * 获取查询条件
     *
     * @param courseQueryRequest
     * @return
     */
    QueryWrapper<Course> getQueryWrapper(CourseQueryRequest courseQueryRequest);

}
