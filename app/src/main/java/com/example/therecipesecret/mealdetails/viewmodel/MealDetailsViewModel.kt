package com.example.therecipesecret.mealdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealDetailsViewModel(private val repository: Repository) :ViewModel(){

    val myRespone: MutableLiveData<MealList> =  MutableLiveData()


   fun getRandomMealInformation(id:String){
      viewModelScope.launch {
         val response = repository.getRandomMealInformation(id)
         myRespone.value=response
      }
   }

   fun insertMeal(meal: Meal){
      viewModelScope.launch(Dispatchers.IO){
         repository.addMeal(meal)
      }
   }

   fun deleteMeal(meal: Meal){
      viewModelScope.launch(Dispatchers.IO){
         repository.deleteMeal(meal)
      }
   }

   fun getAllMeals(){
      viewModelScope.launch(Dispatchers.IO){
         repository.getAllMealsFromDB
      }
   }

   fun updateMeal(meal: Meal){
      viewModelScope.launch(Dispatchers.IO){
         repository.updateMeal(meal)
      }
   }

}