package com.mxf.springbootinit.model.dto.question;

import lombok.Data;

import java.util.List;

@Data
public class AiQuestion {

    private String generate_question;
    private String generate_options;
    private String generate_standard_answer;
    private String explanation;

}