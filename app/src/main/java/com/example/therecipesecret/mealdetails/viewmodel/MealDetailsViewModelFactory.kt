package com.example.therecipesecret.mealdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.home.viewmodel.HomeViewModel

class MealDetailsViewModelFactory(private val repository: Repository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealDetailsViewModel::class.java)) {
            MealDetailsViewModel(repository) as T
        } else {
            throw IllegalArgumentException("MealDetailsViewModel Class not found")
        }
    }
}