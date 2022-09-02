package com.example.therecipesecret.categorymealsdetails.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.therecipesecret.categorymealsdetails.viewmodel.CategoryMealsViewModel
import com.example.therecipesecret.categorymealsdetails.viewmodel.CategoryMealsViewModelFactory
import com.example.therecipesecret.common.model.CategoryMealsDetails
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.ActivityCategoryMealsBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.home.view.HomeFragment


class CategoryMealsActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryMealsBinding
    lateinit var categoryViewModel: CategoryMealsViewModel
    lateinit var categoryMealsAdapter: CategoryMealsAdapter

    lateinit var categoryName: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getViewModel()

        getInformationFromIntent()
        prepareCategoryMealsRecyclerView()
        observeCategoryMealsDetails()

    }

    private fun getViewModel() {
        val mealDao = MealDataBase.getDataBaseInstance(applicationContext).getMealDao()
        val mealApi = RetrofitInstance.api
        val repository = Repository(mealDao,mealApi )
        val categoryViewModelFactory = CategoryMealsViewModelFactory(repository)
        categoryViewModel = ViewModelProvider(this,
            categoryViewModelFactory).get(CategoryMealsViewModel::class.java)
    }

    private fun getInformationFromIntent() {
        val intent = getIntent()
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!

    }

    private fun observeCategoryMealsDetails() {
        categoryViewModel.getCategoryMealsDetails(categoryName)
        categoryViewModel.myRespone.observe(this, Observer {
            categoryMealsAdapter.setCategoryMeals(it.meals as ArrayList<CategoryMealsDetails>)
            binding.tvCategoryCount.text = "${categoryName} meals count : " + it.meals.size
            Log.d("test", it.meals.size.toString())
        })

    }

    private fun prepareCategoryMealsRecyclerView() {

        categoryMealsAdapter = CategoryMealsAdapter()
        binding.recMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryMealsAdapter
        }
    }



}