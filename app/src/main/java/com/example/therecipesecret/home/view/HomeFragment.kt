package com.example.therecipesecret.home.view


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.therecipesecret.R
import com.example.therecipesecret.bottomsheet.view.MealBottomSheet
import com.example.therecipesecret.categorymealsdetails.view.CategoryMealsActivity

import com.example.therecipesecret.common.model.Category

import com.example.therecipesecret.common.model.PopularMeals
import com.example.therecipesecret.mealdetails.view.MealActivity
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentHomeBinding

import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel: HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter: CategoryAdapter
 //   private lateinit var searchedMeals : List<Meal>


    // 3 keys for the extra for the Intent
    companion object {
        const val MEAL_ID = "com.example.therecipesecret.home.view.idMeal"
        const val MEAL_NAME = "com.example.therecipesecret.home.view.strMeal"
        const val MEAL_THUMB = "com.example.therecipesecret.home.view.strMealThumb"
        const val CATEGORY_NAME = "com.example.therecipesecret.home.view.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()

        popularItemAdapter = MostPopularAdapter()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preparePopularItemsRecyclerView()
        prepareCategoriesRecyclerView()

        displayRandomMeal()
        onRandomMealClick()

        observePopularItems()
        observeCategories()

        onPopularItemClick()

        onCategoryItemClick()

        onPopularItemLongClick()

        onSearchIconClick()


    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }


    private fun onPopularItemLongClick() {
        popularItemAdapter.onLongItemClick = {
            val mealBottomSheetFragment = MealBottomSheet.newInstance(it.idMeal)
            mealBottomSheetFragment.show(childFragmentManager, "mealInfo")
        }
    }


    private fun getViewModel() {

        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val homeViewModelFactory = HomeViewModelFactory(repository)
        homeViewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun onCategoryItemClick() {
        categoriesAdapter.onItemClick = {
            val intent = Intent(activity, CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME, it.strCategory)
            startActivity(intent)
        }
    }

    private fun onPopularItemClick() {
        popularItemAdapter.onItemClick = {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, it.idMeal)
            intent.putExtra(MEAL_NAME, it.strMeal)
            intent.putExtra(MEAL_THUMB, it.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoryAdapter()
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context, 3, VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    /* fun displayRandomMeal(){
         homeViewModel.getRandomMeal()
         // observe the mutabledata object"myResponse"
         homeViewModel.myRespone.observe(viewLifecycleOwner, Observer { response->

             randomMeal=response.meals[0]
             var s=randomMeal.strMealThumb
             Picasso.get()
                 .load(s)
                 .into(binding.imgRandomMeal)


         })
     }*/

    private fun displayRandomMeal() {
        homeViewModel.getRandomMeal()
        // observe the mutabledata object"myResponse"
        homeViewModel.myRespone.observe(viewLifecycleOwner, Observer {

            randomMeal = it.meals[0]
            var s = randomMeal.strMealThumb
            Picasso.get()
                .load(s)
                .into(binding.imgRandomMeal)


        })
    }

    private fun onRandomMealClick() {
        binding.randmMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB, randomMeal.strMealThumb)
            startActivity(intent)

        }

    }

    private fun observePopularItems() {
        homeViewModel.getPopularItems("Seafood")
        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            popularItemAdapter.setMeal(it.meals as ArrayList<PopularMeals>)
        })
    }


    // setup RecyclerView
    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            // set the layoutManager
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            adapter = popularItemAdapter
        }
    }

    private fun observeCategories() {
        homeViewModel.getCategories()
        homeViewModel.categoriesResponse.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setCategoryList(it.categories as ArrayList<Category>)
        })
    }



}