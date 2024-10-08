package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxf.springbootinit.annotation.AuthCheck;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;

import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.course.CourseAddRequest;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;

import com.mxf.springbootinit.model.dto.course.UploadCourseRequest;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.Question;
import com.mxf.springbootinit.service.CourseService;
import com.mxf.springbootinit.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;




/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Resource
    private CourseService courseService;

//    @Resource
//    private CourseService userService;

    /**
     * 创建用户
     * //     * @param courseAddRequest
//     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object addCourse(@RequestBody CourseAddRequest courseAddRequest) {
        if (courseAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Course course = new Course();
        BeanUtils.copyProperties(courseAddRequest, course);
        boolean result = courseService.save(course);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(course.getId());
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCourse(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = courseService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     *
     * @param courseQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<com.mxf.springbootinit.model.entity.Course>> listCourseByPage(@RequestBody CourseQueryRequest courseQueryRequest) {
        if (courseQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = courseQueryRequest.getCurrent();
        long size = Math.min(courseQueryRequest.getPageSize(), 20L); // 限制每页大小不超过20
        // 使用courseService的page方法进行分页查询
        Page<com.mxf.springbootinit.model.entity.Course> coursePage = courseService.page(new Page<>(current, size), courseService.getQueryWrapper(courseQueryRequest));
        return ResultUtils.success(coursePage); // 直接返回分页结果
    }

    /**
     * 更新用户
     *
     * @param uploadCourseRequest

     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateCourse(@RequestBody UploadCourseRequest uploadCourseRequest) {
        if (uploadCourseRequest == null || uploadCourseRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Course course = new Course();
        BeanUtils.copyProperties(uploadCourseRequest, course);
        boolean result = courseService.updateById(course);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @PostMapping("/get")
    public BaseResponse<Course> getCourseById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Course course = courseService.getById(id);
        ThrowUtils.throwIf(course == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(course);
    }

}
