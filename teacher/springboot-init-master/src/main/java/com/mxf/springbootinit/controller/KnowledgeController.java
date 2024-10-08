package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeAddRequest;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeQueryRequest;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeUpdateRequest;
import com.mxf.springbootinit.model.entity.Knowledge;
import com.mxf.springbootinit.service.KnowledgeService;
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
@RequestMapping("/knowledge")
@Slf4j
public class KnowledgeController {

    @Resource
    private KnowledgeService knowledgeService;

    @Resource
    private UserService userService;

    private final static Gson GSON = new Gson();

    // region 增删改查

    /**
     * 创建
     *
     * @param knowledgeAddRequest
     * @param
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addKnowledge(@RequestBody KnowledgeAddRequest knowledgeAddRequest) {
        if (knowledgeAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeAddRequest, knowledge);
        boolean result = knowledgeService.save(knowledge);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        long newKnowledgeId = knowledge.getId();
        return ResultUtils.success(newKnowledgeId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteKnowledge(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        long id = deleteRequest.getId();
        // 判断是否存在
        Knowledge oldKnowledge = knowledgeService.getById(id);
        ThrowUtils.throwIf(oldKnowledge == null, ErrorCode.NOT_FOUND_ERROR);

        boolean b = knowledgeService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param knowledgeUpdateRequest
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateKnowledge(@RequestBody KnowledgeUpdateRequest knowledgeUpdateRequest) {
        if (knowledgeUpdateRequest == null || knowledgeUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(knowledgeUpdateRequest, knowledge);

        // 参数校验
//        knowledgeService.validKnowledge(knowledge, false);
        long id = knowledgeUpdateRequest.getId();
        // 判断是否存在
        Knowledge oldKnowledge = knowledgeService.getById(id);
        ThrowUtils.throwIf(oldKnowledge == null, ErrorCode.NOT_FOUND_ERROR);
        boolean result = knowledgeService.updateById(knowledge);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<Knowledge> getKnowledgeById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Knowledge knowledge = knowledgeService.getById(id);
        if (knowledge == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        return ResultUtils.success(knowledge);
    }


    /**
     * 分页获取题目列表（仅管理员）
     *
     * @param knowledgeQueryRequest
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Knowledge>> listKnowledgeByPage(@RequestBody KnowledgeQueryRequest knowledgeQueryRequest,
                                                           HttpServletRequest request) {
        long current = knowledgeQueryRequest.getCurrent();
        long size = knowledgeQueryRequest.getPageSize();
        Page<Knowledge> knowledgePage = knowledgeService.page(new Page<>(current, size),
                knowledgeService.getQueryWrapper(knowledgeQueryRequest));
        return ResultUtils.success(knowledgePage);
    }

    // endregion

}