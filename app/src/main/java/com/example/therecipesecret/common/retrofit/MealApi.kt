package com.example.therecipesecret.common.retrofit

import com.example.therecipesecret.common.model.CategoryList
import com.example.therecipesecret.common.model.CategoryMealsDetailsList
import com.example.therecipesecret.common.model.PopularMealsList
import com.example.therecipesecret.common.model.MealList
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    // to display the random meal on the imageview in home
    @GET("random.php")
   suspend fun getRandomMeal(): MealList

   @GET("lookup.php")
   suspend fun getRandomMealInformation(@Query("i") idMeal:String):MealList

   @GET("filter.php")
   suspend fun getPopularItems(@Query("c") categoryName:String):PopularMealsList

   @GET("categories.php")
   suspend fun getCategories():CategoryList

   @GET("filter.php")
   suspend fun getMealsByCategoryName(@Query("c")strCategory:String):CategoryMealsDetailsList
}