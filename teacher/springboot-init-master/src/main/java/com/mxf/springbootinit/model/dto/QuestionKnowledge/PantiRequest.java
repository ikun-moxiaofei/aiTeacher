package com.mxf.springbootinit.model.dto.QuestionKnowledge;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PantiRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 试卷 id
     */
    private Long shijuanId;

    /**
     * 用户 id
     */
    private Long userId;

    private static final long serialVersionUID = 1L;

}
