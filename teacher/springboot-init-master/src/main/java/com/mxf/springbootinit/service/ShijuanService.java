package com.mxf.springbootinit.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mxf.springbootinit.model.dto.course.CourseQueryRequest;
import com.mxf.springbootinit.model.dto.shijuan.ShijuanQueryRequest;
import com.mxf.springbootinit.model.entity.Course;
import com.mxf.springbootinit.model.entity.Shijuan;

/**
* @author JD
* @description 针对表【shijuan(试卷)】的数据库操作Service
* @createDate 2024-08-22 21:24:54
*/
public interface ShijuanService extends IService<Shijuan> {

    QueryWrapper<Shijuan> getQueryWrapper(ShijuanQueryRequest shijuanQueryRequest);

}
