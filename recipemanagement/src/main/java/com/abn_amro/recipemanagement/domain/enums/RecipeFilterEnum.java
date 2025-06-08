package com.abn_amro.recipemanagement.domain.enums;


public enum RecipeFilterEnum {
    INGREDIENTS,
    VEGETARIAN,
    INSTRUCTIONS,
    SERVINGS;

    public String getValue() {
        return name().toLowerCase();
    }

    @Override
    public String toString() {
        return getValue();
    }
}

