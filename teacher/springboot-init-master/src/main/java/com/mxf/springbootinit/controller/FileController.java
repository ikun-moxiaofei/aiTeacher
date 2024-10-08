package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxf.springbootinit.annotation.AuthCheck;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;



import java.io.BufferedReader;
import java.io.File;

import com.mxf.springbootinit.model.dto.file.FileQueryRequest;


import com.mxf.springbootinit.model.dto.file.FileQueryRequest_;

import com.mxf.springbootinit.service.FileService;
import com.mxf.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

//import static com.sun.jna.ELFAnalyser.ArmAeabiAttributesTag.File;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private UserService userService;



    @PostMapping("/add")
    public Object addFile(MultipartFile[] multipartFiles,Long userId,Long courseId) {
//        String rootPath = "/home/dir";
         String rootPath = "D:\\zhuomian\\ai-teacher\\teacher\\springboot-init-master\\src\\main\\java\\com\\mxf\\springbootinit";

        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        com.mxf.springbootinit.model.entity.File file_ = new  com.mxf.springbootinit.model.entity.File();
//        User user = userService.getLoginUser(request);
        file_.setFilename(multipartFiles[0].getOriginalFilename());
        file_.setUserid(userId);
        file_.setCreatetime(new Date());
        file_.setCourseId(courseId);
        file_.setFilesize(String.valueOf(multipartFiles[0].getSize()));

        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }
        String storagePath = "";
        try {
            if (multipartFiles != null && multipartFiles.length > 0) {
                for (int i = 0; i < multipartFiles.length; i++) {
                    try {
                        //以原来的名称命名,覆盖掉旧的，这里也可以使用UUID之类的方式命名，这里就没有处理了
                        storagePath = rootPath + multipartFiles[i].getOriginalFilename();
                        System.out.println("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + "," + multipartFiles[i].getOriginalFilename()
                                + "，保存的路径为：" + storagePath);
                        multipartFiles[i].transferTo(new File(storagePath));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean result = fileService.save(file_);
        return ResultUtils.success(result);

    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteFile(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     *
     * @param fileQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<com.mxf.springbootinit.model.entity.File>> listFileByPage(@RequestBody FileQueryRequest fileQueryRequest) {
        if (fileQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = fileQueryRequest.getCurrent();
        long size = Math.min(fileQueryRequest.getPageSize(), 20L); // 限制每页大小不超过20
        // 使用fileService的page方法进行分页查询
        Long id_ = fileQueryRequest.getUserid();
        FileQueryRequest_ fileQueryRequest_ = new FileQueryRequest_();

        BeanUtils.copyProperties(fileQueryRequest,fileQueryRequest_);
        fileQueryRequest_.setUserid(id_);

        Page<com.mxf.springbootinit.model.entity.File> filePage = fileService.page(new Page<>(current, size), fileService.getQueryWrapper(fileQueryRequest_));
        return ResultUtils.success(filePage); // 直接返回分页结果
    }



}
