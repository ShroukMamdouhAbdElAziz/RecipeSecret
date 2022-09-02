package com.example.therecipesecret.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.MealList
import kotlinx.coroutines.selects.select
import retrofit2.http.DELETE
import retrofit2.http.Query

@Dao
interface MealDao {

    //by adding (onConflict = OnConflictStrategy.REPLACE) ,
    // means if we insert meal already inserted before it will be replaced by the updated one
    // so work for insert and update at the same time
   // @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Insert
    suspend fun insertMeal(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)


   @androidx.room.Query("SELECT* FROM meals_information")
   // not using suspend as the fun will return livedata
    fun getAllMeals():LiveData<List<Meal>>

}