package com.abn_amro.recipemanagement.controller;


import com.abn_amro.recipemanagement.config.LogRequestResponse;
import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.exception.ErrorResponseDto;
import com.abn_amro.recipemanagement.service.RecipeService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@Validated
@LogRequestResponse(excludeFields = {"password"})
@Tag(name = "Recipe Management Controller", description = "Recipe Management operations")
@Slf4j
public class RecipeController {
    private  final RecipeService recipeService ;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @Operation(
            summary = "Create Recipe REST API",
            description = "Recipe creation endpoint")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/")
    @RateLimiter(name = "recipeApiLimiter")
    public ResponseEntity<ApiResponse<Long>> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        Recipe recipe = recipeService.createRecipe(recipeDTO) ;
        ApiResponse<Long> response = ApiResponse.success(recipe.getId(),"", ResponseConstant.STATUS_201,
                ResponseConstant.MESSAGE_201,false);;
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recipe.getId())
                .toUri();
        response.setUrl(location.toString());
        return ResponseEntity.created(location)
                .body(response);
      }

    @Operation(
            summary = "Get recipe by Id REST API",
            description = "Endpoint to get a recipe")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RecipeDTO>> getUserById(@PathVariable(name = "id") @Pattern(regexp = "\\d++", message = "only int is expected") Long id) {
        RecipeDTO recipeDTO = recipeService.GetRecipeByRecipeId(id) ;
        return ResponseEntity.ok(ApiResponse.success(recipeDTO,null,ResponseConstant.STATUS_200,ResponseConstant.MESSAGE_200,false));
    }

    @Operation(description = "Get all recipe and search by filter")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "20o",
                    description = "HTTP Status GET"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Cacheable(value = "recipeSearchCache", key = "T(java.util.Objects).hash(#vegetarian, #servings, #firstname, #ingredient,#page,#size)")
    @GetMapping("/search_recipe")
    public ResponseEntity<ApiResponse<Page<RecipeDTO>>> searchRecipe(
            @RequestParam boolean vegetarian,
            @RequestParam Integer servings,
            @RequestParam String ingredient,
            @RequestParam String instruction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size) ;
      Page<RecipeDTO> recipeDTOPage =   recipeService.searchRecipeWithDynamicFiltering(vegetarian,servings,
              ingredient,instruction,pageable);
      return ResponseEntity.ok(ApiResponse.success(recipeDTOPage,null,ResponseConstant.STATUS_200,ResponseConstant.MESSAGE_200,false));
    }


    @Operation(description = "Get all recipe and search by filter using id of user")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status GET"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @Cacheable(value = "recipeSearchCache", key = "T(java.util.Objects).hash(#vegetarian, #servings, #firstname, #ingredient,#page,#size)")
    @GetMapping("/search_recipe_by_userid/{userId}")
    public ResponseEntity<ApiResponse<Page<RecipeDTO>>> searchRecipeByUserId(
            @PathVariable(name = "userId") Long userId,
            @RequestParam boolean vegetarian,
            @RequestParam Integer servings,
            @RequestParam String ingredient,
            @RequestParam String instruction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size) ;
        Page<RecipeDTO> recipeDTOPage =   recipeService.searchRecipeWithDynamicFilteringByUserId(userId,vegetarian,servings,
                ingredient,instruction,pageable);
        return ResponseEntity.ok(ApiResponse.success(recipeDTOPage,null,ResponseConstant.STATUS_200,ResponseConstant.MESSAGE_200,false));
    }

    @GetMapping("/gatewayserver")
    public ResponseEntity<String> testGateway(){
        return ResponseEntity.ok("Api gateway is sending request");
    }

}




































































































































































