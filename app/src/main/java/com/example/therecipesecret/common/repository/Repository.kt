package com.example.therecipesecret.common.repository

import androidx.lifecycle.LiveData
import com.example.therecipesecret.common.model.*
import com.example.therecipesecret.common.retrofit.MealApi
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.db.MealDao
import com.example.therecipesecret.db.MealDataBase

class Repository(private val mealDao: MealDao, private val mealApi: MealApi) {

    suspend fun getRandomMeal(): MealList {
        return mealApi.getRandomMeal()
    }

    suspend fun getRandomMealInformation(idMeal: String): MealList {
        return mealApi.getRandomMealInformation(idMeal)
    }

    suspend fun getPopularItems(categoryName: String): PopularMealsList {
        return mealApi.getPopularItems(categoryName)
    }

    suspend fun getCategories(): CategoryList {
        return mealApi.getCategories()
    }

    suspend fun getMealsByCategoryName(strCategory: String): CategoryMealsDetailsList {
        return mealApi.getMealsByCategoryName(strCategory)
    }

    suspend fun searchMeals(searchQuery: String): MealList {
        return mealApi.searchMeals(searchQuery)
    }


    val getAllMealsFromDB: LiveData<List<Meal>> = mealDao.getAllMeals()

    suspend fun addMeal(meal: Meal) {
        mealDao.insertMeal(meal)
    }

    suspend fun deleteMeal(meal: Meal) {
        mealDao.deleteMeal(meal)
    }

    suspend fun updateMeal(meal: Meal) {
        mealDao.updateMeal(meal)
    }


}