package com.mxf.springbootinit.model.dto.shiianSubmit;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShijuanSubmitQueryRequest extends PageRequest implements Serializable {


    /**
     * id
     */
    private Long id;

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

    private static final long serialVersionUID = 1L;
}
