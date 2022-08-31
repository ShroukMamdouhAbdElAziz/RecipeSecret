package com.example.therecipesecret.common.retrofit

import com.example.therecipesecret.common.model.CategoryMealList
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealApi {

    // to display the random meal on the imageview in home
    @GET("random.php")
   suspend fun getRandomMeal(): MealList

   @GET("lookup.php")
   suspend fun getRandomMealInformation(@Query("i") idMeal:String):MealList

   @GET("filter.php")
   suspend fun getPopularItems(@Query("c") categoryName:String):CategoryMealList
}