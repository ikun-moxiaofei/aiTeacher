package com.mxf.springbootinit.model.dto.QuestionKnowledge;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionKnowledgeEditRequest implements Serializable {
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