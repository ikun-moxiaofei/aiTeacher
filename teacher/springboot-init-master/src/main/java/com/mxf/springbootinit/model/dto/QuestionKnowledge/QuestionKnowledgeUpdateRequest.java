package com.mxf.springbootinit.model.dto.QuestionKnowledge;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionKnowledgeUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 课程 id
     */
    private Long knowledgeId;


    private static final long serialVersionUID = 1L;
}