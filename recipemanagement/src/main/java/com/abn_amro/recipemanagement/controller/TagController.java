package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "Tag Management Controller", description = "Tag Management operations")
@Slf4j
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {

}
