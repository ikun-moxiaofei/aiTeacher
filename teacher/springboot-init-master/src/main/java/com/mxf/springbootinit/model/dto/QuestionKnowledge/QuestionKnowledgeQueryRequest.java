package com.mxf.springbootinit.model.dto.QuestionKnowledge;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionKnowledgeQueryRequest extends PageRequest implements Serializable {

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