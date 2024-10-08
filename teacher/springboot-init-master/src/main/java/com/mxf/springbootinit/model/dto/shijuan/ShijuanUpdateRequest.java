package com.mxf.springbootinit.model.dto.shijuan;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ShijuanUpdateRequest implements Serializable {


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
     * 创建用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}
