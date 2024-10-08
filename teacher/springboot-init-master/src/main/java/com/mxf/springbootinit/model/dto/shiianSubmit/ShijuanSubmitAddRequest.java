package com.mxf.springbootinit.model.dto.shiianSubmit;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户创建请求
 *
 */
@Data
public class ShijuanSubmitAddRequest implements Serializable {

    /**
     * 用户答案（json 数组）
     */
    private List<answer> answer;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 试卷 id
     */
    private Long shijuanId;

    /**
     * 用户 id
     */
    private Long userId;

    /**
     * 成绩
     */
    private Integer grade;

    /**
     * 总分
     */
    private Integer sumGrade;

    /**
     * 使用时间
     */
    private Date userTime;

    private static final long serialVersionUID = 1L;
}