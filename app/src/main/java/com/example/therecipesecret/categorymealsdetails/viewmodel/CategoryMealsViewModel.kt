package com.example.therecipesecret.categorymealsdetails.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.CategoryMealsDetailsList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.launch


class CategoryMealsViewModel(private val repository: Repository): ViewModel(){
    var myRespone: MutableLiveData<CategoryMealsDetailsList> = MutableLiveData()

    fun getCategoryMealsDetails(strCategory:String){

            viewModelScope.launch {
                val response = repository.getMealsByCategoryName(strCategory)
                // myRespone.postValue(response)
                myRespone.value= response
            }
        }

}