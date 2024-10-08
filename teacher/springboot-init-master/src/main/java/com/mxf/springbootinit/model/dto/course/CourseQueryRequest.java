package com.mxf.springbootinit.model.dto.course;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 任课老师ID
     */
    private Long teacherId;

    /**
     * 课程简介
     */
    private String courseDescription;

}