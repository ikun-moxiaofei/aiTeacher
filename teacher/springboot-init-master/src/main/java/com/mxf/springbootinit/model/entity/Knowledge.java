package com.mxf.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 知识点表
 * @TableName knowledge
 */
@TableName(value ="knowledge")
@Data
public class Knowledge implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
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

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}