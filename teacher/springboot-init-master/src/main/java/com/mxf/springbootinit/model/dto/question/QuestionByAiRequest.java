package com.mxf.springbootinit.model.dto.question;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionByAiRequest implements Serializable {
    private String query;
    private String kbName;
    /**
     * 课程id
     */
    private Long courseId;
    /**
     * 出题老师ID
     */
    private Long teacherId;

    public Integer choice;
}
