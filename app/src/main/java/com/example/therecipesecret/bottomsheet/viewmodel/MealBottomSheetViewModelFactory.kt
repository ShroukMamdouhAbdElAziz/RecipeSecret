package com.example.therecipesecret.bottomsheet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.common.repository.Repository

class MealBottomSheetViewModelFactory (private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MealBottomSheetViewModel::class.java)) {
            MealBottomSheetViewModel(repository) as T
        } else {
            throw IllegalArgumentException("MealBottomSheetViewModel Class not found")
        }
    }

}