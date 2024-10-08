package com.mxf.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.ShijuanSubmitMapper;
import com.mxf.springbootinit.model.dto.shiianSubmit.ShijuanSubmitQueryRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanQueryRequest;
import com.mxf.springbootinit.model.entity.Shijuan;
import com.mxf.springbootinit.model.entity.ShijuanSubmit;
import com.mxf.springbootinit.service.ShijuanSubmitService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author JD
* @description 针对表【shijuan_submit(shijuan提交)】的数据库操作Service实现
* @createDate 2024-08-22 21:24:58
*/
@Service
public class ShijuanSubmitServiceImpl extends ServiceImpl<ShijuanSubmitMapper, ShijuanSubmit>
    implements ShijuanSubmitService {

    @Override
    public QueryWrapper<ShijuanSubmit> getQueryWrapper(ShijuanSubmitQueryRequest shijuanSubmitQueryRequest) {
        if (shijuanSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = shijuanSubmitQueryRequest.getId();
        Integer status = shijuanSubmitQueryRequest.getStatus();
        Long shijuanId = shijuanSubmitQueryRequest.getShijuanId();
        Long userId = shijuanSubmitQueryRequest.getUserId();
        Integer grade = shijuanSubmitQueryRequest.getGrade();
        Integer sumGrade = shijuanSubmitQueryRequest.getSumGrade();

        QueryWrapper<ShijuanSubmit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(status != null, "status", status);
        queryWrapper.eq(shijuanId != null, "shijuanId", shijuanId);
        queryWrapper.eq(grade != null, "grade", grade);
        queryWrapper.eq(sumGrade != null, "sumGrade", sumGrade);
        queryWrapper.eq(userId != null, "userId", userId);
        String sortField = shijuanSubmitQueryRequest.getSortField();
        String sortOrder = shijuanSubmitQueryRequest.getSortOrder();
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}




