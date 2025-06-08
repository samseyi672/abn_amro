package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import com.abn_amro.recipemanagement.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recipe")
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "User Management Controller", description = "User Management crud operations")
@Slf4j
public class RecipeController {

    private  final RecipeService recipeService ;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


}
