package com.abn_amro.recipemanagement.domain.dto.request;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.thymeleaf.context.Context;

import java.util.List;

@Data
@ToString
@Builder
public class Mail {
    private String mailFrom;
    private String mailTo;
    private String[] ccTo;
    private String mailSubject;
    private String contentType;
    private String thymeleafTemplateName;
    Context thymeleafContext;
    List<Attachment> attachment;
}



















