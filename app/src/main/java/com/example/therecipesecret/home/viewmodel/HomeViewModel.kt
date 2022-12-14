package com.example.therecipesecret.home.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therecipesecret.common.model.CategoryList
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.model.PopularMealsList
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.repository.Repository
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    var myRespone: MutableLiveData<MealList> = MutableLiveData()
    var result: MutableLiveData<PopularMealsList> = MutableLiveData()
    var categoriesResponse: MutableLiveData<CategoryList> = MutableLiveData()
    var searchedMealsLiveData:MutableLiveData<MealList> =  MutableLiveData()



    fun getRandomMeal() {
        viewModelScope.launch {
            val response = repository.getRandomMeal()
            // myRespone.postValue(response)
            myRespone.value = response
        }
    }

    fun getPopularItems(categoryName: String) {
        viewModelScope.launch {
            val response = repository.getPopularItems(categoryName)
            result.value = response

        }

    }

    fun getCategories() {
        viewModelScope.launch {
            val response = repository.getCategories()
            categoriesResponse.value = response
        }
    }

    // for search
    fun getMealBySearch(searchQuery: String){
        viewModelScope.launch {
            val mealList = repository.searchMeals(searchQuery)
          mealList?.let {
                searchedMealsLiveData.postValue(it)
            }
        }
    }
    // for search
    fun observerSearchMealLiveData():LiveData<MealList> = searchedMealsLiveData


}