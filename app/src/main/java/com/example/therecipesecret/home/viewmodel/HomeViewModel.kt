package com.example.therecipesecret.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.CategoryMealList
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository):ViewModel(){

    var myRespone:MutableLiveData<MealList> =  MutableLiveData()
    var result:MutableLiveData<CategoryMealList> = MutableLiveData()


    fun getRandomMeal(){
        viewModelScope.launch {
            val response = repository.getRandomMeal()
           // myRespone.postValue(response)
            myRespone.value= response
        }
    }

    fun getPopularItems(categoryName:String){
        viewModelScope.launch {
            var response= repository.getPopularItems(categoryName)
            result.value=response

        }

    }




}