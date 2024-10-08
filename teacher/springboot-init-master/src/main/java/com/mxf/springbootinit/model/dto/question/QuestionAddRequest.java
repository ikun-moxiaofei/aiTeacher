package com.mxf.springbootinit.model.dto.question;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 *
 */
@Data
public class QuestionAddRequest implements Serializable {

    /**
     * 题干
     */
    private String stem;

    /**
     * 课程id
     */
    private Long courseId;

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

    private static final long serialVersionUID = 1L;
}