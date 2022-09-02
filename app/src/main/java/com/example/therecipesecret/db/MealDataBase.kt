package com.example.therecipesecret.db

import android.content.Context
import androidx.room.*
import com.example.therecipesecret.common.model.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)

 abstract class MealDataBase:RoomDatabase() {

     abstract fun mealDao():MealDao   // get reference from interface

     companion object{
         @Volatile
         var INSTANCE:MealDataBase?= null

         @Synchronized
         fun getDbInstance(context: Context):MealDataBase{
             if (INSTANCE==null){
                 INSTANCE= Room.databaseBuilder(
                               context,
                               MealDataBase::class.java,
                               "meal_DB"
                 ).fallbackToDestructiveMigration().build()

             }

             return INSTANCE as MealDataBase
         }

     }


}