package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserAddRequest;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserQueryRequest;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserUpdateRequest;
import com.mxf.springbootinit.model.entity.CourseUser;
import com.mxf.springbootinit.service.CourseUserService;
import com.mxf.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子接口
 *
 */
@RestController
@RequestMapping("/courseUser")
@Slf4j
public class CourseUserController {

    @Resource
    private CourseUserService courseUserService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param courseUserAddRequest
     * @param
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addCourseUser(@RequestBody CourseUserAddRequest courseUserAddRequest) {
        if (courseUserAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseUser courseUser = new CourseUser();
        BeanUtils.copyProperties(courseUserAddRequest, courseUser);
        boolean result = courseUserService.save(courseUser);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newCourseUserId = courseUser.getId();
        return ResultUtils.success(newCourseUserId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCourseUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();
        // 判断是否存在
        CourseUser oldCourseUser = courseUserService.getById(id);
        ThrowUtils.throwIf(oldCourseUser == null, ErrorCode.NOT_FOUND_ERROR);

        boolean b = courseUserService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param courseUserUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateCourseUser(@RequestBody CourseUserUpdateRequest courseUserUpdateRequest) {
        if (courseUserUpdateRequest == null || courseUserUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseUser courseUser = new CourseUser();
        BeanUtils.copyProperties(courseUserUpdateRequest, courseUser);

        // 参数校验
//        courseUserService.validCourseUser(courseUser, false);
        long id = courseUserUpdateRequest.getId();
        // 判断是否存在
        CourseUser oldCourseUser = courseUserService.getById(id);
        ThrowUtils.throwIf(oldCourseUser == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = courseUserService.updateById(courseUser);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<CourseUser> getCourseUserById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        CourseUser courseUser = courseUserService.getById(id);
        if (courseUser == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(courseUser);
    }


    /**
     * 分页获取题目列表（仅管理员）
     *
     * @param courseUserQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<CourseUser>> listCourseUserByPage(@RequestBody CourseUserQueryRequest courseUserQueryRequest,
                                                                             HttpServletRequest request) {
        long current = courseUserQueryRequest.getCurrent();
        long size = courseUserQueryRequest.getPageSize();
        Page<CourseUser> courseUserPage = courseUserService.page(new Page<>(current, size),
                courseUserService.getQueryWrapper(courseUserQueryRequest));
        return ResultUtils.success(courseUserPage);
    }

    // endregion

}