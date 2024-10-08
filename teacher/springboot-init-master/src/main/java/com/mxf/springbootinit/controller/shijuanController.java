package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.CourseUser.CourseUserQueryRequest;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.question.QuestionQueryRequest;
import com.mxf.springbootinit.model.dto.question.selectRandomQuestionsRequest;
import com.mxf.springbootinit.model.dto.shiianSubmit.ShijuanSubmitUpdateRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanAddRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanQueryRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanUpdateRequest;
import com.mxf.springbootinit.model.dto.shijuan.question;
import com.mxf.springbootinit.model.entity.*;
import com.mxf.springbootinit.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;




/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/shijuan")
@Slf4j
public class shijuanController {

    @Resource
    private ShijuanService shijuanService;

    @Resource
    private QuestionController questionController;

    @Resource
    private CourseUserService courseUserService;

    @Resource
    private ShijuanSubmitService shijuanSubmitService;

    private final static Gson GSON = new Gson();

//    @Resource
//    private ShijuanService userService;

    /**
     * 创建用户
     * //     * @param shijuanAddRequest
//     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object addShijuan(@RequestBody ShijuanAddRequest shijuanAddRequest) {
        if (shijuanAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Shijuan shijuan = new Shijuan();
        BeanUtils.copyProperties(shijuanAddRequest, shijuan);

        selectRandomQuestionsRequest selectRandomQuestionsRequest = new selectRandomQuestionsRequest();
        selectRandomQuestionsRequest.setCourseId(shijuanAddRequest.getCourseID());
        selectRandomQuestionsRequest.setState_1(shijuanAddRequest.getState_1());
        selectRandomQuestionsRequest.setState_0(shijuanAddRequest.getState_0());
        List<Question> questions = questionController.selectRandomQuestions(selectRandomQuestionsRequest);
        if (questions != null) {
            shijuan.setQuestions(GSON.toJson(questions));
        }
        boolean result = shijuanService.save(shijuan);

        CourseUserQueryRequest courseUserQueryRequest = new CourseUserQueryRequest();
        courseUserQueryRequest.setId(shijuanAddRequest.getCourseID());
        QueryWrapper<CourseUser> queryWrapper = courseUserService.getQueryWrapper(courseUserQueryRequest);
        List<CourseUser> courseUserList = courseUserService.list(queryWrapper);
        // 遍历查询结果，提取userId
        for (CourseUser courseUser : courseUserList) {
            Long userId = courseUser.getUserId();
            ShijuanSubmit shijuanSubmit = new ShijuanSubmit();
            shijuanSubmit.setUserId(userId);
            shijuanSubmit.setShijuanId(shijuan.getId());
            shijuanSubmit.setStatus(0);
            shijuanSubmitService.save(shijuanSubmit);
        }

        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(shijuan.getId());
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteShijuan(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = shijuanService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     *
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Shijuan>> listShijuanByPage(@RequestBody ShijuanQueryRequest shijuanQueryRequest) {
        if (shijuanQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = shijuanQueryRequest.getCurrent();
        long size = Math.min(shijuanQueryRequest.getPageSize(), 20L); // 限制每页大小不超过20
        // 使用shijuanService的page方法进行分页查询

        Page<Shijuan> shijuanPage = shijuanService.page(new Page<>(current, size), shijuanService.getQueryWrapper(shijuanQueryRequest));
        return ResultUtils.success(shijuanPage); // 直接返回分页结果
    }

    /**
     * 更新用户
     *
     * @param uploadShijuanRequest

     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateShijuan(@RequestBody ShijuanUpdateRequest uploadShijuanRequest) {
        if (uploadShijuanRequest == null || uploadShijuanRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Shijuan shijuan = new Shijuan();
        BeanUtils.copyProperties(uploadShijuanRequest, shijuan);
        List<question> questions = uploadShijuanRequest.getQuestions();
        if (questions != null) {
            shijuan.setQuestions(GSON.toJson(questions));
        }
        boolean result = shijuanService.updateById(shijuan);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

}
