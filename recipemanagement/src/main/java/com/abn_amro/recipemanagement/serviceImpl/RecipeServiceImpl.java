package com.abn_amro.recipemanagement.serviceImpl;

import com.abn_amro.recipemanagement.constant.ResponseConstant;
import com.abn_amro.recipemanagement.domain.dto.request.IngredientDTO;
import com.abn_amro.recipemanagement.domain.dto.request.RecipeDTO;
import com.abn_amro.recipemanagement.domain.dto.request.UserDTO;
import com.abn_amro.recipemanagement.domain.dto.response.ApiResponse;
import com.abn_amro.recipemanagement.domain.entities.Ingredient;
import com.abn_amro.recipemanagement.domain.entities.Recipe;
import com.abn_amro.recipemanagement.domain.mapper.IngredientMapper;
import com.abn_amro.recipemanagement.domain.mapper.RecipeMapper;
import com.abn_amro.recipemanagement.event.RecipeRegisteredEvent;
import com.abn_amro.recipemanagement.exception.RecipeNotFoundEception;
import com.abn_amro.recipemanagement.repository.IngredientRepository;
import com.abn_amro.recipemanagement.repository.RecipeRepository;
import com.abn_amro.recipemanagement.service.RecipeService;
import com.abn_amro.recipemanagement.service.UserServiceClient;
import com.abn_amro.recipemanagement.utils.RecipeSpecification;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class RecipeServiceImpl implements RecipeService {
    private  final RecipeRepository recipeRepository ;
    private final RecipeMapper recipeMapper;

    private final IngredientRepository ingredientRepository ;
    private final IngredientMapper ingredientMapper;
    private final UserServiceClient userServiceClient;

    private final ApplicationEventPublisher eventPublisher;


    @Override
    public RecipeDTO findById(Long id) {
        return recipeRepository.findById(id)
                .map(recipeMapper::toDto).orElseThrow(() -> new RecipeNotFoundEception("Recipe is not found"));
    }

    public Page<RecipeDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size) ;
        return recipeRepository.findAll(pageable).map(recipeMapper::toDto) ;
    }

    @Override
    public Recipe createRecipe(RecipeDTO recipeDTO) {
        UserDTO user =callGetUserByUserIdService(recipeDTO);
        final Recipe recipe = recipeMapper.toEntity(recipeDTO);
        List<IngredientDTO> ingredientDTO = recipeDTO.getIngredients() ;
        List<Ingredient> ingredientList  = ingredientMapper.toEntityList(ingredientDTO);
        List<Ingredient> ingredients =ingredientList.stream().map(ingredient -> {
            ingredient.setRecipe(recipe);
            return ingredient ;
        }).collect(Collectors.toList());
        //ingredientRepository.saveAll(ingredients) ;
        Recipe createdRecipe = recipeRepository.save(recipe);
        log.info("created recipe "+createdRecipe);
        sendRecipeCreationEvent(user, recipe, ingredientDTO);
        return recipe;
    }

    private void sendRecipeCreationEvent(UserDTO user, Recipe recipe, List<IngredientDTO> ingredientDTO) {
        RecipeRegisteredEvent recipeRegisteredEvent  =  RecipeRegisteredEvent.builder()
                                                        .title(recipe.getTitle())
                                                        .instructions(recipe.getInstructions())
                                                        .lastName(user.getLastName())
                                                         .firstName(user.getFirstName())
                                                        .userName(user.getUserName())
                                                        .ingredients(ingredientDTO)
                                                        .servings(recipe.getServings())
                                                        .vegetarian(recipe.isVegetarian())
                                                        .build();
        eventPublisher.publishEvent(recipeRegisteredEvent);
    }

    private UserDTO callGetUserByUserIdService(RecipeDTO recipeDTO) {
        ApiResponse<UserDTO> response = userServiceClient.getUserById(recipeDTO.getUserId()).getBody();
        UserDTO user = response.getData();
        log.info("Creating recipe for user: {} {}", user.getFirstName(), user.getLastName());
        return response.getData();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return null;
    }

    @Override
    public Page<RecipeDTO> searchRecipeWithDynamicFiltering(Boolean vegetarian, Integer servings, String ingredient, String instructionSearchText, Pageable pageable) {
        Specification<Recipe> spec = Specification.where(RecipeSpecification.hasRecipeVegetarian(vegetarian))
                .and(RecipeSpecification.recipeHasServings(servings))
                .and(RecipeSpecification.recipeHasIngredient(ingredient))
                .and(RecipeSpecification.recipeContainsInstruction(instructionSearchText));
        return recipeRepository.findAll(spec, pageable).map(recipeMapper::toDto);
    }
}

















































































































