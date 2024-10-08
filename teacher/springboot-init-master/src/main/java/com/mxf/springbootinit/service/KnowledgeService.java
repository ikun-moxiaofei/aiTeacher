package com.mxf.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeQueryRequest;
import com.mxf.springbootinit.model.dto.question.QuestionQueryRequest;
import com.mxf.springbootinit.model.entity.Knowledge;
import com.mxf.springbootinit.model.entity.Question;

/**
* @author JD
* @description 针对表【knowledge(知识点表)】的数据库操作Service
* @createDate 2024-08-18 20:36:22
*/
public interface KnowledgeService extends IService<Knowledge> {
    QueryWrapper<Knowledge> getQueryWrapper(KnowledgeQueryRequest knowledgeQueryRequest);

}
