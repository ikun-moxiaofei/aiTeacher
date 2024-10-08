package com.mxf.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.constant.CommonConstant;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.model.dto.question.QuestionQueryRequest;
import com.mxf.springbootinit.model.entity.File;
import com.mxf.springbootinit.service.QuestionService;
import com.mxf.springbootinit.utils.SqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mxf.springbootinit.mapper.QuestionMapper;
import com.mxf.springbootinit.model.entity.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author JD
* @description 针对表【question(课程)】的数据库操作Service实现
* @createDate 2024-07-31 20:25:45
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService {

    @Override
    public QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = 1L;
        if (questionQueryRequest.getId() != null) {
            id = questionQueryRequest.getId();
        }else {
            id = null;
        }


        Long teacherId = 1L;
        if (questionQueryRequest.getTeacherId() != null) {
            teacherId = questionQueryRequest.getTeacherId();
        }else{
            teacherId = null;
        }

        Long courseId = 1L;
        if (questionQueryRequest.getCourseId() != null) {
            courseId = questionQueryRequest.getCourseId();
        }else{
            courseId = null;
        }

        String stem = questionQueryRequest.getStem();


        Date createtime = questionQueryRequest.getCreateTime();

        Integer state = 10;
        if (questionQueryRequest.getState() != null) {
            state = questionQueryRequest.getState();
        }else{
            state = null;
        }

        Integer byAi = 10;
        if (questionQueryRequest.getByAi() != null) {
            byAi = questionQueryRequest.getByAi();
        }else{
            byAi = null;
        }

        int current = questionQueryRequest.getCurrent();
        int pageSize = questionQueryRequest.getPageSize();
        String sortField = questionQueryRequest.getSortField();
        String sortOrder = questionQueryRequest.getSortOrder();

        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(stem), "stem", stem);
        queryWrapper.eq(teacherId != null, "teacherId", teacherId);
        queryWrapper.eq(courseId != null, "courseId", courseId);
        queryWrapper.eq(createtime != null, "createtime", createtime);
        queryWrapper.eq(state != null, "state", state);
        queryWrapper.eq(byAi != null, "byAi", byAi);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Autowired
    private QuestionMapper questionMapper;

    public List<Question> selectRandomQuestions(Long courseId, Integer state_0, Integer state_1) {
        // 这里需要实现具体的随机选择逻辑
        // 你可能需要使用两个QueryWrapper来分别查询state为0和1的题目
        // 然后随机选择指定数量的题目，并将它们合并到一个列表中

        List<Question> questionsState0 = questionMapper.selectRandomByState(courseId, 0, state_0);
        List<Question> questionsState1 = questionMapper.selectRandomByState(courseId, 1, state_1);

        // 合并两个列表
        List<Question> allRandomQuestions = new ArrayList<>(questionsState0);
        allRandomQuestions.addAll(questionsState1);

        return allRandomQuestions;
    }
}




