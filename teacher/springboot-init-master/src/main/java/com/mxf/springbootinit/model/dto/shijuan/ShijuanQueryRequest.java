package com.mxf.springbootinit.model.dto.shijuan;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ShijuanQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 创建用户 id
     */
    private Long userId;
    /**
     * 截止时间
     */
    private Date deadlineTime;

    private static final long serialVersionUID = 1L;
}
