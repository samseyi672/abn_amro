package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.CommentDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.exception.ErrorResponseDto;
import com.abn_amro.recipemanagement.service.CommentService;
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
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "Comment Management Controller", description = "Comment Management operations")
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "Create a new comment",
            description = "Creates a new comment with the provided details")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Comment successfully created"
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
    public ResponseEntity<ApiResponse<CommentDTO>> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO savedComment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(savedComment, null, ResponseConstant.STATUS_201, ResponseConstant.MESSAGE_201,false));
    }


    @Operation(
            summary = "Get comment by Recipe ID",
            description = "Retrieves a comment by its recipeid")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Comment successfully retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Commment not found",
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
    @Cacheable(value = "commentCache", key = "#commentId")
    @RateLimiter(name = "recipeApiLimiter")
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByRecipe(@PathVariable Long recipeId) {
        List<CommentDTO> comments = commentService.getCommentsByRecipeId(recipeId);
        return ResponseEntity.ok(ApiResponse.success(comments, null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false));
    }


    @Operation(
            summary = "Get comment by User ID",
            description = "Retrieves a comment by its userid")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Comment successfully retrieved"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Commment not found",
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
    @Cacheable(value = "userCommentIdCache", key = "#userCommentId")
    @RateLimiter(name = "recipeApiLimiter")
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<CommentDTO>>> getCommentsByUser(@PathVariable Long userId) {
        List<CommentDTO> comments = commentService.getCommentsByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(comments, null, ResponseConstant.STATUS_200, ResponseConstant.MESSAGE_200,false));
    }
}




