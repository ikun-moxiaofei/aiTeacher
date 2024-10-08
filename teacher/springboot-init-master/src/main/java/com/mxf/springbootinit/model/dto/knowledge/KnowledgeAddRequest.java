package com.mxf.springbootinit.model.dto.knowledge;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 */
@Data
public class KnowledgeAddRequest implements Serializable {

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