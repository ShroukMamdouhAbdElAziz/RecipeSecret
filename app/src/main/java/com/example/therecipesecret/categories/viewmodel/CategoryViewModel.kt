package com.example.therecipesecret.categories.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.CategoryList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.launch


class CategoryViewModel(private val repository: Repository): ViewModel(){
    var categoriesResponse: MutableLiveData<CategoryList> = MutableLiveData()

    fun getCategories() {
        viewModelScope.launch {
            var response = repository.getCategories()
            categoriesResponse.value = response
        }
    }

}