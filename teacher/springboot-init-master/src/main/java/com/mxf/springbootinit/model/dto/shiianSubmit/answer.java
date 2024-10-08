package com.mxf.springbootinit.model.dto.shiianSubmit;


import lombok.Data;

@Data
public class answer {
    /**
     * id
     */
    private Long id;

    /**
     * 题干
     */
    private String stem;

    /**
     * 题型
     */
    private Integer state;

    /**
     * 选项
     */
    private String options;

    /**
     * 答案
     */
    private String answer;

    /**
     * 分值
     */
    private int grade;

    /**
     * 得分
     */
    private int getGrade;



}
