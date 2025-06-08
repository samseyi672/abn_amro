package com.abn_amro.recipemanagement.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RecipeNotFoundEception extends RuntimeException {
    public RecipeNotFoundEception(String recipe_is_not_found) {
    }
}
