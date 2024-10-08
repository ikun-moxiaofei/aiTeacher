package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeAddRequest;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeQueryRequest;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeUpdateRequest;
import com.mxf.springbootinit.model.entity.QuestionKnowledge;
import com.mxf.springbootinit.service.QuestionKnowledgeService;
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
@RequestMapping("/questionKnowledge")
@Slf4j
public class QuestionKnowledgeController {

    @Resource
    private QuestionKnowledgeService questionKnowledgeService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param questionKnowledgeAddRequest
     * @param
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addQuestionKnowledge(@RequestBody QuestionKnowledgeAddRequest questionKnowledgeAddRequest) {
        if (questionKnowledgeAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionKnowledge questionKnowledge = new QuestionKnowledge();
        BeanUtils.copyProperties(questionKnowledgeAddRequest, questionKnowledge);
        boolean result = questionKnowledgeService.save(questionKnowledge);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newQuestionKnowledgeId = questionKnowledge.getId();
        return ResultUtils.success(newQuestionKnowledgeId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestionKnowledge(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();
        // 判断是否存在
        QuestionKnowledge oldQuestionKnowledge = questionKnowledgeService.getById(id);
        ThrowUtils.throwIf(oldQuestionKnowledge == null, ErrorCode.NOT_FOUND_ERROR);

        boolean b = questionKnowledgeService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionKnowledgeUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateQuestionKnowledge(@RequestBody QuestionKnowledgeUpdateRequest questionKnowledgeUpdateRequest) {
        if (questionKnowledgeUpdateRequest == null || questionKnowledgeUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionKnowledge questionKnowledge = new QuestionKnowledge();
        BeanUtils.copyProperties(questionKnowledgeUpdateRequest, questionKnowledge);

        // 参数校验
//        questionKnowledgeService.validQuestionKnowledge(questionKnowledge, false);
        long id = questionKnowledgeUpdateRequest.getId();
        // 判断是否存在
        QuestionKnowledge oldQuestionKnowledge = questionKnowledgeService.getById(id);
        ThrowUtils.throwIf(oldQuestionKnowledge == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = questionKnowledgeService.updateById(questionKnowledge);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<QuestionKnowledge> getQuestionKnowledgeById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QuestionKnowledge questionKnowledge = questionKnowledgeService.getById(id);
        if (questionKnowledge == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(questionKnowledge);
    }


    /**
     * 分页获取题目列表（仅管理员）
     *
     * @param questionKnowledgeQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<QuestionKnowledge>> listQuestionKnowledgeByPage(@RequestBody QuestionKnowledgeQueryRequest questionKnowledgeQueryRequest,
                                                                             HttpServletRequest request) {
        long current = questionKnowledgeQueryRequest.getCurrent();
        long size = questionKnowledgeQueryRequest.getPageSize();
        Page<QuestionKnowledge> questionKnowledgePage = questionKnowledgeService.page(new Page<>(current, size),
                questionKnowledgeService.getQueryWrapper(questionKnowledgeQueryRequest));
        return ResultUtils.success(questionKnowledgePage);
    }

    // endregion

}