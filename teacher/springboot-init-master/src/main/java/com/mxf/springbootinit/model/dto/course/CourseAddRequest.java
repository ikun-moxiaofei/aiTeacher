package com.mxf.springbootinit.model.dto.course;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户创建请求
 *
 */
@Data
public class CourseAddRequest implements Serializable {

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