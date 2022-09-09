package com.example.therecipesecret.categories.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.therecipesecret.R
import com.example.therecipesecret.categories.viewmodel.CategoryViewModel
import com.example.therecipesecret.categories.viewmodel.CategoryViewModelFactory
import com.example.therecipesecret.categorymealsdetails.view.CategoryMealsActivity
import com.example.therecipesecret.categorymealsdetails.view.CategoryMealsAdapter
import com.example.therecipesecret.common.model.Category
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentCategoriesBinding
import com.example.therecipesecret.databinding.FragmentHomeBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.home.view.CategoryAdapter
import com.example.therecipesecret.home.view.HomeFragment
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoryAdapter: CategoryAdapter
    lateinit var categoryViewModel: CategoryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()




    }

    private fun getViewModel() {

        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val categoryViewModelFactory = CategoryViewModelFactory(repository)
        categoryViewModel =
            ViewModelProvider(this, categoryViewModelFactory).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment

        binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeCategories()
        onCategoryItemClick()
    }

    private fun setUpRecyclerView() {
        categoryAdapter = CategoryAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    fun observeCategories() {
        categoryViewModel.getCategories()
        categoryViewModel.categoriesResponse.observe(viewLifecycleOwner, Observer {
            categoryAdapter.setCategoryList(it.categories as ArrayList<Category>)
        })
    }

    private fun onCategoryItemClick() {
        categoryAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

}



