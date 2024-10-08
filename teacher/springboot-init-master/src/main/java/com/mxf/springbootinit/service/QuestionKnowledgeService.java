package com.mxf.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxf.springbootinit.model.dto.QuestionKnowledge.QuestionKnowledgeQueryRequest;
import com.mxf.springbootinit.model.dto.knowledge.KnowledgeQueryRequest;
import com.mxf.springbootinit.model.entity.Knowledge;
import com.mxf.springbootinit.model.entity.QuestionKnowledge;

/**
* @author JD
* @description 针对表【question_knowledge(题目知识点对应表)】的数据库操作Service
* @createDate 2024-08-18 20:36:13
*/
public interface QuestionKnowledgeService extends IService<QuestionKnowledge> {
    QueryWrapper<QuestionKnowledge> getQueryWrapper(QuestionKnowledgeQueryRequest questionKnowledgeQueryRequest);

}
