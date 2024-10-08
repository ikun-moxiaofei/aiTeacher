package com.mxf.springbootinit.model.dto.question;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 */
@Data
public class selectRandomQuestionsRequest implements Serializable {

    private Long courseId;

    private int state_0;

    private int state_1;

    private static final long serialVersionUID = 1L;
}