package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.TagDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.exception.ErrorResponseDto;
import com.abn_amro.recipemanagement.service.TagService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "Tag Management Controller", description = "Tag Management operations")
@Slf4j
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService;


    @Operation(
            summary = "Create a new Tag",
            description = "Creates a new Tag with the provided details")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Tag successfully created"
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
    public ResponseEntity<ApiResponse<TagDTO>> createTag(@RequestBody TagDTO tagDTO) {
        TagDTO created = tagService.createTag(tagDTO);
        return ResponseEntity.ok(ApiResponse.success(created, null, ResponseConstant.STATUS_201, ResponseConstant.MESSAGE_201,false));
    }

    @Operation(
            summary = "Get Tag by Pagination",
            description = "Retrieves list of Tags")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Tag successfully retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Tag not found",
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
    @Cacheable(value = "tagCache", key = "#alltagId")
    @RateLimiter(name = "recipeApiLimiter")
    @GetMapping()
    public ResponseEntity<ApiResponse<List<TagDTO>>> getAllTags( @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(tagService.getAllTags(page, size), null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false));
    }
}






































































































































