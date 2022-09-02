package com.example.therecipesecret.common.repository

import com.example.therecipesecret.common.model.CategoryList
import com.example.therecipesecret.common.model.CategoryMealsDetailsList
import com.example.therecipesecret.common.model.PopularMealsList
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.retrofit.RetrofitInstance

class Repository {

    suspend fun getRandomMeal():MealList{
        return RetrofitInstance.api.getRandomMeal()
    }

    suspend fun getRandomMealInformation(idMeal:String):MealList{
        return  RetrofitInstance.api.getRandomMealInformation(idMeal)
    }

    suspend fun getPopularItems(categoryName:String): PopularMealsList{
        return RetrofitInstance.api.getPopularItems(categoryName)
    }

    suspend fun getCategories():CategoryList{
        return RetrofitInstance.api.getCategories()
    }

    suspend fun getMealsByCategoryName(strCategory:String):CategoryMealsDetailsList{
        return RetrofitInstance.api.getMealsByCategoryName(strCategory)
    }

}