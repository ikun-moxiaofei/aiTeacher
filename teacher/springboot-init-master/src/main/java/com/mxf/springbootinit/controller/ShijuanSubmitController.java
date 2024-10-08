package com.mxf.springbootinit.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mxf.springbootinit.common.BaseResponse;
import com.mxf.springbootinit.common.DeleteRequest;
import com.mxf.springbootinit.common.ErrorCode;
import com.mxf.springbootinit.common.ResultUtils;
import com.mxf.springbootinit.exception.BusinessException;
import com.mxf.springbootinit.exception.ThrowUtils;

import com.mxf.springbootinit.model.dto.QuestionKnowledge.PantiRequest;
import com.mxf.springbootinit.model.dto.shiianSubmit.*;


import com.mxf.springbootinit.model.entity.Question;
import com.mxf.springbootinit.model.entity.ShijuanSubmit;
import com.mxf.springbootinit.service.QuestionService;
import com.mxf.springbootinit.service.ShijuanSubmitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


/**
 * 用户接口
 *
 */
@RestController
@RequestMapping("/shijuanSubmit")
@Slf4j
public class ShijuanSubmitController {

    @Resource
    private ShijuanSubmitService shijuanSubmitService;

    @Resource
    private QuestionService questionService;

    private final  Gson GSON = new Gson();

//    @Resource
//    private ShijuanSubmitService userService;

    /**
     * 创建用户
     * //     * @param shijuanSubmitAddRequest
     //     * @param request
     * @return
     */
    @PostMapping("/add")
    public Object addShijuanSubmit(@RequestBody ShijuanSubmitAddRequest shijuanSubmitAddRequest) {
        if (shijuanSubmitAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ShijuanSubmit shijuanSubmit = new ShijuanSubmit();
        BeanUtils.copyProperties(shijuanSubmitAddRequest, shijuanSubmit);
        List<answer> answer = shijuanSubmitAddRequest.getAnswer();
        if (answer != null) {
            shijuanSubmit.setAnswer(GSON.toJson(answer));
        }
        boolean result = shijuanSubmitService.save(shijuanSubmit);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(shijuanSubmit.getId());
    }

    public answer[] convertJsonArrayToAnswerArray(String jsonArray) {
        // 创建Gson实例
        Gson gson = new Gson();

        // 指定Answer[]的TypeToken，确保正确反序列化数组
        TypeToken<answer[]> typeToken = new TypeToken<answer[]>() {};

        // 使用Gson的fromJson方法将JSON字符串反序列化为Answer对象数组

        return gson.fromJson(jsonArray, typeToken.getType());
    }

    @PostMapping("/panti")
    public CompletableFuture<BaseResponse<Boolean>> panti(@RequestBody PantiRequest pantiRequest) {
        // 使用 CompletableFuture 进行异步处理
        return CompletableFuture.supplyAsync(() -> {
            // 参数校验
            if (pantiRequest == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }

            ShijuanSubmitQueryRequest shijuanSubmitQueryRequest = new ShijuanSubmitQueryRequest();

            if (pantiRequest.getShijuanId() != null) {
                Long shijuanId = pantiRequest.getShijuanId();
                shijuanSubmitQueryRequest.setShijuanId(shijuanId);
            }

            if (pantiRequest.getId() != null) {
                Long Id = pantiRequest.getId();
                shijuanSubmitQueryRequest.setId(Id);
            }

            if (pantiRequest.getUserId() != null) {
                Long UserId = pantiRequest.getUserId();
                shijuanSubmitQueryRequest.setUserId(UserId);
            }

            int grade = 0;     // 当前题目的得分
            int sumGrade = 0;  // 总得分

            // 获取符合查询条件的试卷提交记录
            Page<ShijuanSubmit> shijuanSubmitPage = shijuanSubmitService.page(
                    new Page<>(),
                    shijuanSubmitService.getQueryWrapper(shijuanSubmitQueryRequest)
            );

            for (ShijuanSubmit submit : shijuanSubmitPage.getRecords()) {
                // 获取并解析学生答案的 JSON 字符串
                String answerJson = submit.getAnswer();
                answer[] answers = convertJsonArrayToAnswerArray(answerJson);

                for (answer answerTemp : answers) {
                    Long questionId = answerTemp.getId();
                    Question question = questionService.getById(questionId);

                    // 如果题目为客观题（state=0），使用原判题逻辑
                    if (question.getState() == 0) {
                        answerTemp.setGrade(5);
                        if (Objects.equals(question.getAnswer(), answerTemp.getAnswer())) {

                            answerTemp.setGetGrade(answerTemp.getGrade());
                            grade += answerTemp.getGrade(); // 累加当前题目得分
                        } else {

                            answerTemp.setGetGrade(0);
                        }
                        sumGrade += answerTemp.getGrade(); // 累加总得分
                    }
                    // 如果题目为主观题（state=1），使用模型 API 进行判题
                    else if (question.getState() == 1) {
                        answerTemp.setGrade(10);
                        String subjectiveQuestion = question.getStem(); // 主观题题目
                        String studentAnswer = answerTemp.getAnswer();          // 学生答案
                        String standardAnswer = question.getAnswer();           // 标准答案

                        // 调用评估主观题的接口获取评分
                        int subjectiveScore = evaluateSubjectiveQuestion(subjectiveQuestion, studentAnswer, standardAnswer);
                        answerTemp.setGetGrade(subjectiveScore); // 设置评分
                        grade += subjectiveScore;               // 累加当前题目得分
                        sumGrade += answerTemp.getGrade();      // 累加总得分
                    }
                }

                // 将更新后的答案数组转换回 JSON 字符串
                String updatedAnswerJson = GSON.toJson(answers);
                submit.setAnswer(updatedAnswerJson);
                submit.setGrade(grade);       // 更新当前题目的得分
                submit.setSumGrade(sumGrade); // 更新总得分
                submit.setStatus(1);          // 更新状态

                grade = 0;     // 重置当前题目得分
                sumGrade = 0;  // 重置总得分

                // 保存更新后的提交记录
                shijuanSubmitService.saveOrUpdate(submit);
            }

            return ResultUtils.success(true); // 返回成功结果
        });
    }

    @PostMapping("/subjective")
    public BaseResponse<Integer> evaluateSubjective(@RequestBody SubjectiveEvaluationRequest request) {
        // 参数校验
        if (request == null
                || request.getSubjectiveQuestion() == null
                || request.getStudentAnswer() == null
                || request.getStandardAnswer() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        // 调用主观题评分逻辑
        int score = evaluateSubjectiveQuestion(
                request.getSubjectiveQuestion(),
                request.getStudentAnswer(),
                request.getStandardAnswer()
        );

        // 返回评分结果
        return ResultUtils.success(score);
    }



    private int evaluateSubjectiveQuestion(String subjectiveQuestion, String studentAnswer, String standardAnswer) {
        // 构造向 /subjective_judgment/ 端点的请求
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("subjective_question", subjectiveQuestion);
        requestBody.put("student_answer", studentAnswer);
        requestBody.put("standard_answer", standardAnswer);

        RestTemplate restTemplate = new RestTemplate();
        System.out.println(requestBody);

        int maxRetries = 5;  // 最大重试次数
        int retryCount = 0;  // 当前重试次数

        while (retryCount < maxRetries) {
            try {
                ResponseEntity<SubjectiveJudgmentResponse> response = restTemplate.postForEntity(
                        "http://82.156.232.115:8000/subjective_judgment/",
                        requestBody,
                        SubjectiveJudgmentResponse.class
                );
                System.out.println(response.getBody());

                // 处理响应并返回分数
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return response.getBody().getTotalScore();
                } else {
                    throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题评估过程中发生错误");
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
                    throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题评估过程中发生服务端错误: " + e.getMessage());
                }
            } catch (Exception e) {
                // 其他异常
                throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题评估过程中发生未知错误: " + e.getMessage());
            }
        }

        // 如果超过最大重试次数仍然失败
        throw new BusinessException(ErrorCode.API_CALL_ERROR, "主观题评估请求重试次数超过限制，仍然失败");
    }



    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteShijuanSubmit(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = shijuanSubmitService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     *
     * @param shijuanSubmitQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<ShijuanSubmit>> listShijuanSubmitByPage(@RequestBody ShijuanSubmitQueryRequest shijuanSubmitQueryRequest) {
        if (shijuanSubmitQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = shijuanSubmitQueryRequest.getCurrent();
        long size = shijuanSubmitQueryRequest.getPageSize();
        // 使用shijuanSubmitService的page方法进行分页查

        Page<ShijuanSubmit> shijuanSubmitPage = shijuanSubmitService.page(new Page<>(current, size), shijuanSubmitService.getQueryWrapper(shijuanSubmitQueryRequest));
        return ResultUtils.success(shijuanSubmitPage); // 直接返回分页结果
    }

    /**
     * 更新用户


     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateShijuanSubmit(@RequestBody ShijuanSubmitUpdateRequest shijuanSubmitUpdateRequest) {
        if (shijuanSubmitUpdateRequest == null || shijuanSubmitUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ShijuanSubmit shijuanSubmit = new ShijuanSubmit();
        BeanUtils.copyProperties(shijuanSubmitUpdateRequest, shijuanSubmit);
        List<answer> answer = shijuanSubmitUpdateRequest.getAnswer();
        if (answer != null) {
            shijuanSubmit.setAnswer(GSON.toJson(answer));
        }
        boolean result = shijuanSubmitService.updateById(shijuanSubmit);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

}
