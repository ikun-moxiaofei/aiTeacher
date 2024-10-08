package com.mxf.springbootinit.model.dto.shijuan;


import lombok.Data;

@Data
public class question {
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
     * 分值
     */
    private String grade;



}
