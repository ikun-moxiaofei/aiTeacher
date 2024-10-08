package com.mxf.springbootinit.model.dto.CourseUser;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseUserQueryRequest extends PageRequest implements Serializable {

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