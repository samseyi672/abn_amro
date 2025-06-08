package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeTagDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.exception.ErrorResponseDto;
import com.abn_amro.recipemanagement.service.RecipeTagService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipetags")
@RequiredArgsConstructor
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "RecipeTag Management Controller", description = "RecipeTag Management operations")
@Slf4j
public class RecipeTagController {

    private final RecipeTagService recipeTagService;


    @Operation(
            summary = "Create a new RecipeTag",
            description = "Creates a new RecipeTag with the provided details")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "RecipeTag successfully created"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @RateLimiter(name = "recipeApiLimiter")
    @PostMapping
    public ResponseEntity<ApiResponse<RecipeTagDTO>> create(@Valid @RequestBody RecipeTagDTO dto) {
        RecipeTagDTO saved = recipeTagService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(saved, null, ResponseConstant.STATUS_201, ResponseConstant.MESSAGE_201,false));
    }


    @Operation(
            summary = "Get RecipeTag by Recipe ID",
            description = "Retrieves a RecipeTag by its recipeid")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "RecipeTag successfully retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "RecipeTag not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Cacheable(value = "recipeTagCache", key = "#recipeTagId")
    @RateLimiter(name = "recipeApiLimiter")
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<List<RecipeTagDTO>>> getByRecipeId(@PathVariable Long recipeId) {
        return ResponseEntity.ok(ApiResponse.success(
                recipeTagService.findByRecipeId(recipeId), null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false));
    }

    @Operation(
            summary = "Get RecipeTag by Recipe ID",
            description = "Retrieves a RecipeTag by its recipeid")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "RecipeTag successfully retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "RecipeTag not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Cacheable(value = "tagIdCache", key = "#tagIdCache")
    @RateLimiter(name = "recipeApiLimiter")
    @GetMapping("/tag/{tagId}")
    public ResponseEntity<ApiResponse<List<RecipeTagDTO>>> getByTagId(@PathVariable Long tagId) {
        return ResponseEntity.ok(ApiResponse.success(
                recipeTagService.findByTagId(tagId), null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false));
    }
}
























































































































































































