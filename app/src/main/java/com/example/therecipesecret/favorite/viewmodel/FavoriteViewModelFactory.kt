package com.example.therecipesecret.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.common.repository.Repository


class FavoriteViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            FavoriteViewModel(repository) as T
        } else {
            throw IllegalArgumentException("FavoriteViewModel Class not found")
        }
    }

}