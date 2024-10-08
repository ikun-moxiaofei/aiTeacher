package com.mxf.springbootinit.model.dto.file;

import com.mxf.springbootinit.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FileQueryRequest_ extends PageRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 创建用户id
     */
    private Long userid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**

     */
    private Integer courseId;

    private static final long serialVersionUID = 1L;
}