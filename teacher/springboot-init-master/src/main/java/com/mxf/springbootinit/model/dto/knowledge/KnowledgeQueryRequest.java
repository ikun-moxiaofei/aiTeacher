package com.mxf.springbootinit.model.dto.knowledge;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KnowledgeQueryRequest extends PageRequest implements Serializable {

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