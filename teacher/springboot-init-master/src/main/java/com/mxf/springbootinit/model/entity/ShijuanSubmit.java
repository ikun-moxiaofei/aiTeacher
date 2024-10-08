package com.mxf.springbootinit.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * shijuan提交
 * @TableName shijuan_submit
 */
@TableName(value ="shijuan_submit")
@Data
public class ShijuanSubmit implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户答案（json 数组）
     */
    private String answer;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 使用时间
     */
    private Date userTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}