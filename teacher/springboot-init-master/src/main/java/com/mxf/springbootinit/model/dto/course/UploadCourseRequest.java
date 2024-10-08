package com.mxf.springbootinit.model.dto.course;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传请求
 *
 */
@Data
public class UploadCourseRequest implements Serializable {
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