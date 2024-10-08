package com.mxf.springbootinit.model.dto.knowledge;

import lombok.Data;

import java.io.Serializable;

@Data
public class KnowledgeEditRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 知识点名
     */
    private String stem;

    /**
     * 创建老师ID
     */
    private Long teacherId;

    /**
     * 课程 id
     */
    private Long courseId;

    private static final long serialVersionUID = 1L;
}