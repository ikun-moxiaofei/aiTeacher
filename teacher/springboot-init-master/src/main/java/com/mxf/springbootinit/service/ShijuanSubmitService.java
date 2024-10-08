package com.mxf.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxf.springbootinit.model.dto.shiianSubmit.ShijuanSubmitQueryRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanQueryRequest;
import com.mxf.springbootinit.model.entity.Shijuan;
import com.mxf.springbootinit.model.entity.ShijuanSubmit;

/**
* @author JD
* @description 针对表【shijuan_submit(shijuan提交)】的数据库操作Service
* @createDate 2024-08-22 21:24:58
*/
public interface ShijuanSubmitService extends IService<ShijuanSubmit> {

    QueryWrapper<ShijuanSubmit> getQueryWrapper(ShijuanSubmitQueryRequest shijuanSubmitQueryRequest);

}
