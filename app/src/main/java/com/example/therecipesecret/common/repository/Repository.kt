package com.example.therecipesecret.common.repository

import com.example.therecipesecret.common.model.CategoryMealList
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import retrofit2.http.Query

class Repository {

    suspend fun getRandomMeal():MealList{
        return RetrofitInstance.api.getRandomMeal()
    }

    suspend fun getRandomMealInformation(idMeal:String):MealList{
        return  RetrofitInstance.api.getRandomMealInformation(idMeal)
    }

    suspend fun getPopularItems(categoryName:String): CategoryMealList{
        return RetrofitInstance.api.getPopularItems(categoryName)
    }


}