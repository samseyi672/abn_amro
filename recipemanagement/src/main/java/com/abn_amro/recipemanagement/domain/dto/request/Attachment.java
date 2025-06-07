package com.abn_amro.recipemanagement.domain.dto.request;


import lombok.Data;
import org.springframework.core.io.InputStreamSource;

@Data
public class Attachment {
    private String attachmentFileName;
    private String attachmentContentType;
    InputStreamSource attachmentSource;
}
