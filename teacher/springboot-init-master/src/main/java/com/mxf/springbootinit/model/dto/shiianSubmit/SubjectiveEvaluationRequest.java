package com.mxf.springbootinit.model.dto.shiianSubmit;

import lombok.Data;

@Data
public class SubjectiveEvaluationRequest {
    private String subjectiveQuestion; // 主观题题目
    private String studentAnswer;      // 学生答案
    private String standardAnswer;     // 标准答案
}
