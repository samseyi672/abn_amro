package com.abn_amro.recipemanagement.serviceImpl;

import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.domain.mapper.RecipeMapper;
import com.abn_amro.recipemanagement.repository.RecipeRepository;
import com.abn_amro.recipemanagement.service.RecipeService;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static java.lang.StringTemplate.STR;


@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private  final RecipeRepository recipeRepository ;
    private final RecipeMapper mapper;
    private final DatabaseClient databaseClient ;

    @Override
    public Mono<RecipeDTO> findById(Long id) {
        return null;
    }

    @Override
    public Flux<RecipeDTO> findAll() {
        return recipeRepository.findAll().map(mapper::toDto);
    }

    public Flux<RecipeDTO> findAll(int page, int size) {
        int offset = page * size;
        return databaseClient.sql("SELECT title,id,vegetarian,servings,instructions,user_id FROM recipes LIMIT :limit OFFSET :offset")
                .bind("limit", size)
                .bind("offset", offset)
                .map((row, meta) -> rowMapper(row))
                .all();
    }

    private RecipeDTO rowMapper(Row row){
        return RecipeDTO.builder()
                .id(row.get("id", Long.class))
                .title(row.get("title", String.class))
                .vegetarian(Boolean.TRUE.equals(row.get("vegetarian", Boolean.class)))
                .servings(row.get("servings", Integer.class))
                .instructions(row.get("instructions", String.class))
                .userId(row.get("user_id", Long.class))
                .build();
    }

    @Override
    public Mono<ResponseEntity<ApiResponse<String>>> save(RecipeDTO recipeDTO) {
        return recipeRepository.save(mapper.toEntity(recipeDTO))
                .map(saved -> {
                    Long id = saved.getId();
                    return ResponseEntity.ok(
                            new ApiResponse<String>(
                                    ResponseConstant.STATUS_201,
                                    ResponseConstant.MESSAGE_201,
                                    "Recipe created with id="+id
                            )
                    );
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }
}

















































































































