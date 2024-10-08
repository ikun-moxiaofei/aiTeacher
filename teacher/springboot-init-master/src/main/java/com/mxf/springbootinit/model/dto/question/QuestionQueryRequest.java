package com.mxf.springbootinit.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 题干
     */
    private String stem;

    /**
     * 题型
     */
    private Integer state;

    /**
     * ai
     */
    private Integer byAi;

    /**
     * 选项
     */
    private String options;

    /**
     * 出题老师ID
     */
    private Long teacherId;

    /**
     * 答案
     */
    private String answer;

    /**
     * 解析
     */
    private String parse;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}
