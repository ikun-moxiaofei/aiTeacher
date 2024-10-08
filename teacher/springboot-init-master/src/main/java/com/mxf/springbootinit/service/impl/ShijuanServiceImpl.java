package com.mxf.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.mapper.ShijuanMapper;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanQueryRequest;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.Shijuan;
import com.mxf.springbootinit.service.ShijuanService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author JD
* @description 针对表【shijuan(试卷)】的数据库操作Service实现
* @createDate 2024-08-22 21:24:54
*/
@Service
public class ShijuanServiceImpl extends ServiceImpl<ShijuanMapper, Shijuan>
    implements ShijuanService {

    @Override
    public QueryWrapper<Shijuan> getQueryWrapper(ShijuanQueryRequest shijuanQueryRequest) {
        if (shijuanQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        String title = shijuanQueryRequest.getTitle();
        Long userId = shijuanQueryRequest.getUserId();
        Long id = shijuanQueryRequest.getId();
        QueryWrapper<Shijuan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.eq(userId != null, "userId", userId);
        String sortField = shijuanQueryRequest.getSortField();
        String sortOrder = shijuanQueryRequest.getSortOrder();
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

}




