package com.example.therecipesecret.bottomsheet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.launch

class MealBottomSheetViewModel (private val repository: Repository): ViewModel() {


    val myRespone: MutableLiveData<MealList> = MutableLiveData()


    fun getRandomMealInformation(id: String) {
        viewModelScope.launch {
            val response = repository.getRandomMealInformation(id)
            myRespone.value = response



        }
    }

}