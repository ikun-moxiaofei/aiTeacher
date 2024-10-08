package com.mxf.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.QuestionKnowledgeMapper;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeQueryRequest;
import com.mxf.springbootinit.model.entity.Knowledge;
import com.mxf.springbootinit.model.entity.QuestionKnowledge;
import com.mxf.springbootinit.service.QuestionKnowledgeService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author JD
* @description 针对表【question_knowledge(题目知识点对应表)】的数据库操作Service实现
* @createDate 2024-08-18 20:36:13
*/
@Service
public class QuestionKnowledgeServiceImpl extends ServiceImpl<QuestionKnowledgeMapper, QuestionKnowledge>
    implements QuestionKnowledgeService {

    @Override
    public QueryWrapper<QuestionKnowledge> getQueryWrapper(QuestionKnowledgeQueryRequest questionKnowledgeQueryRequest) {
        if (questionKnowledgeQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = 1L;
        if (questionKnowledgeQueryRequest.getId() != null) {
            id = questionKnowledgeQueryRequest.getId();
        }else {
            id = null;
        }
        Long questionId = 1L;
        if (questionKnowledgeQueryRequest.getQuestionId() != null) {
            questionId = questionKnowledgeQueryRequest.getQuestionId();
        }else {
            questionId = null;
        }
        Long knowledgeId = 1L;
        if (questionKnowledgeQueryRequest.getKnowledgeId() != null) {
            knowledgeId = questionKnowledgeQueryRequest.getKnowledgeId();
        }else {
            knowledgeId = null;
        }

        int current = questionKnowledgeQueryRequest.getCurrent();
        int pageSize = questionKnowledgeQueryRequest.getPageSize();
        String sortField = questionKnowledgeQueryRequest.getSortField();
        String sortOrder = questionKnowledgeQueryRequest.getSortOrder();

        QueryWrapper<QuestionKnowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(questionId != null, "questionId", questionId);
        queryWrapper.eq(knowledgeId != null, "knowledgeId", knowledgeId);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }
}




