package com.mxf.springbootinit.model.dto.CourseUser;

import lombok.Data;

import java.io.Serializable;

@Data
public class CourseUserUpdateRequest implements Serializable {

    /**
     * id
     */

    private Long id;

    /**
     * 课程 id
     */
    private Long courseId;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}