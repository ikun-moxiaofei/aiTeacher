package com.mxf.springbootinit.model.dto.CourseUser;

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
public class CourseUserAddRequest implements Serializable {

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