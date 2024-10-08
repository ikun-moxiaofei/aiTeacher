package com.mxf.springbootinit.model.dto.shijuan;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户创建请求
 *
 */
@Data
public class ShijuanAddRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 题目（json 数组）
     */
    private List<question> questions;

    /**
     * 课程ID
     */
    private Long courseID;

    /**
     * 创建用户 id
     */
    private Long userId;

    private int state_0;

    private int state_1;

    /**
     * 截止时间
     */
    private Date deadlineTime;

    private static final long serialVersionUID = 1L;
}