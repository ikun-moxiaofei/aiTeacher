package com.mxf.springbootinit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;
import com.mxf.springbootinit.model.dto.question.*;
import com.mxf.springbootinit.model.dto.shiianSubmit.SubjectiveJudgmentResponse;
import com.mxf.springbootinit.model.entity.Question;
import com.mxf.springbootinit.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

//    @Resource
//    private QuestionService userService;

    /**
     * 创建用户
     * //     * @param questionAddRequest
//     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object addQuestion(@RequestBody QuestionAddRequest questionAddRequest) {
        if (questionAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddRequest, question);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(question.getId());
    }

    /**
     *
     * @return
     */
    @PostMapping("/addByAi")
    public Object addQuestionByAi(@RequestBody QuestionByAiRequest questionByAiRequest) {
        if (questionByAiRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("query", questionByAiRequest.getQuery());
        requestBody.put("kb_name", questionByAiRequest.getKbName());
        requestBody.put("choice", String.valueOf(questionByAiRequest.getChoice()));

        int maxRetries = 5;  // 最大重试次数
        int retryCount = 0;  // 当前重试次数
        int ans = 0;
        Question question = new Question();

        while (retryCount < maxRetries) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<AiQuestion> response = restTemplate.postForEntity(
                        "http://82.156.232.115:8000/stage_generate_question/",
                        requestBody,
                        AiQuestion.class
                );

                // 解析响应并添加题目
                System.out.println(response.getBody());

                // 处理响应并返回分数
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    String generateQuestion = response.getBody().getGenerate_question();
                    String explanation = response.getBody().getExplanation();
                    String generateOptions = response.getBody().getGenerate_options().toString();
                    String generateStandardAnswer = response.getBody().getGenerate_standard_answer();

                    QuestionAddRequest questionAddRequest = new QuestionAddRequest();
                    questionAddRequest.setStem(generateQuestion);
                    questionAddRequest.setCourseId(questionByAiRequest.getCourseId());
                    questionAddRequest.setState(questionByAiRequest.getChoice());
                    questionAddRequest.setOptions(generateOptions);
                    questionAddRequest.setTeacherId(questionByAiRequest.getTeacherId());
                    questionAddRequest.setAnswer(generateStandardAnswer);
                    questionAddRequest.setParse(explanation);
                    questionAddRequest.setByAi(1);


                    BeanUtils.copyProperties(questionAddRequest, question);
                    boolean result = questionService.save(question);
                    ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
                    ans = Math.toIntExact(question.getId());
                    return ResultUtils.success(question.getId());

                } else {
                    throw new BusinessException(ErrorCode.API_CALL_ERROR, "ai题目生成过程中发生错误");
                }
            } catch (HttpClientErrorException e) {
                // 如果是客户端错误（如4xx），不进行重试
                throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题评估过程中发生客户端错误: " + e.getMessage());
            } catch (HttpServerErrorException e) {
                // 如果是服务端错误（如5xx），尝试重试
                if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    retryCount++;
                    System.out.println("收到500错误，正在重试... (" + retryCount + "/" + maxRetries + ")");
                } else {
                    throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题生成过程中发生服务端错误: " + e.getMessage());
                }
            } catch (Exception e) {
                // 其他异常
                throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题生成过程中发生未知错误: " + e.getMessage());
            }
        }
        return ResultUtils.success(question.getId());
    }


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteQuestion(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = questionService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     *
     * @param questionQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Question>> listQuestionByPage(@RequestBody QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = questionQueryRequest.getCurrent();
        long size = questionQueryRequest.getPageSize();
        // 使用questionService的page方法进行分页查询
        Page<Question> questionPage = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(questionQueryRequest));
        return ResultUtils.success(questionPage); // 直接返回分页结果
    }

    /**
     * 更新用户
     *
     * @param questionQueryRequest

     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateQuestion(@RequestBody QuestionQueryRequest questionQueryRequest) {
        if (questionQueryRequest == null || questionQueryRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionQueryRequest, question);
        boolean result = questionService.updateById(question);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @PostMapping("/get")
    public BaseResponse<Question> getQuestionById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Question question = questionService.getById(id);
        ThrowUtils.throwIf(question == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(question);
    }

    @PostMapping("/random/select")
    public List<Question> selectRandomQuestions(@RequestBody selectRandomQuestionsRequest selectRandomQuestionsRequest) {
        if (selectRandomQuestionsRequest == null || selectRandomQuestionsRequest.getCourseId() == null || selectRandomQuestionsRequest.getCourseId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 这里我们使用count作为总的题目数量，state_0和state_1用于内部逻辑

        // 调用service层的方法进行随机选择
        // 这里需要确保questionService的selectRandomQuestions方法能够接受两个state的参数
        List<Question> randomQuestions = questionService.selectRandomQuestions(selectRandomQuestionsRequest.getCourseId(), selectRandomQuestionsRequest.getState_0(), selectRandomQuestionsRequest.getState_1());

        if (randomQuestions == null || randomQuestions.isEmpty()) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }

        return randomQuestions; // 返回随机选取的题目列表
    }

}
