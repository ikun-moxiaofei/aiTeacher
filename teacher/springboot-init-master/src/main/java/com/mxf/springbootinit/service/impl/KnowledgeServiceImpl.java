package com.mxf.springbootinit.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.KnowledgeMapper;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeQueryRequest;
import com.mxf.springbootinit.model.entity.Knowledge;
import com.mxf.springbootinit.model.entity.Question;
import com.mxf.springbootinit.service.KnowledgeService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author JD
* @description 针对表【knowledge(知识点表)】的数据库操作Service实现
* @createDate 2024-08-18 20:36:22
*/
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge>
    implements KnowledgeService {

    @Override
    public QueryWrapper<Knowledge> getQueryWrapper(KnowledgeQueryRequest knowledgeQueryRequest) {
        if (knowledgeQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = 1L;
        if (knowledgeQueryRequest.getId() != null) {
            id = knowledgeQueryRequest.getId();
        }else {
            id = null;
        }


        Long teacherId = 1L;
        if (knowledgeQueryRequest.getTeacherId() != null) {
            teacherId = knowledgeQueryRequest.getTeacherId();
        }else{
            teacherId = null;
        }

        Long courseId = 1L;
        if (knowledgeQueryRequest.getCourseId() != null) {
            courseId = knowledgeQueryRequest.getCourseId();
        }else{
            courseId = null;
        }

        String stem = knowledgeQueryRequest.getStem();


        int current = knowledgeQueryRequest.getCurrent();
        int pageSize = knowledgeQueryRequest.getPageSize();
        String sortField = knowledgeQueryRequest.getSortField();
        String sortOrder = knowledgeQueryRequest.getSortOrder();

        QueryWrapper<Knowledge> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(stem), "stem", stem);
        queryWrapper.eq(teacherId != null, "teacherId", teacherId);
        queryWrapper.eq(courseId != null, "courseId", courseId);

        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;

    }
}




