package com.example.therecipesecret.categorymealsdetails.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.home.viewmodel.HomeViewModel


class CategoryMealsViewModelFactory(private var repository: Repository):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CategoryMealsViewModel::class.java)) {
            CategoryMealsViewModel(repository) as T
        } else {
            throw IllegalArgumentException("CategoryMealsViewModel Class not found")
        }
    }

}