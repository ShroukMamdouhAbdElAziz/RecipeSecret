package com.example.therecipesecret.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.Meal

import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteViewModel(private val repository: Repository) : ViewModel() {
    var favoriteMeals: LiveData<List<Meal>> = repository.getAllMealsFromDB

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMeal(meal)
        }
    }



    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMeal(meal)
        }
    }
}