package com.example.therecipesecret.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.therecipesecret.common.model.Category
import com.example.therecipesecret.common.model.CategoryList
import com.example.therecipesecret.common.model.PopularMeals
import com.example.therecipesecret.mealdetails.view.MealActivity
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.databinding.FragmentHomeBinding
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var homeViewModel:HomeViewModel
    lateinit var randomMeal: Meal
    lateinit var popularItemAdapter: MostPopularAdapter
    lateinit var categoriesAdapter: CategoryAdapter
    var mealsList = ArrayList<PopularMeals>()

    // 3 keys for the extra for the Intent
    companion object{
        const val MEAL_ID ="com.example.therecipesecret.home.view.idMeal"
        const val MEAL_NAME ="com.example.therecipesecret.home.view.strMeal"
        const val MEAL_THUMB="com.example.therecipesecret.home.view.strMealThumb"
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val homeViewModelFactory = HomeViewModelFactory(repository)
        homeViewModel=ViewModelProvider(this,homeViewModelFactory).get(HomeViewModel::class.java)

        popularItemAdapter= MostPopularAdapter()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding= FragmentHomeBinding.inflate(inflater,container,false)
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




    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoryAdapter()
       binding.recViewCategories.apply {
          layoutManager=GridLayoutManager(context,3, VERTICAL,false)
           adapter=categoriesAdapter
       }
    }

    fun displayRandomMeal(){
        homeViewModel.getRandomMeal()
        // observe the mutabledata object"myResponse"
        homeViewModel.myRespone.observe(viewLifecycleOwner, Observer { response->

            randomMeal=response.meals[0]
            var s=randomMeal.strMealThumb
            Glide.with(this)
                .load(s)
                .into(binding.imgRandomMeal)


        })
    }

    fun onRandomMealClick(){
        binding.randmMealCard.setOnClickListener {
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)

        }

    }

    fun observePopularItems(){
        homeViewModel.getPopularItems("Seafood")
        homeViewModel.result.observe(viewLifecycleOwner, Observer {
            popularItemAdapter.setMeal(mealsList)
        })
    }


    // setup RecyclerView
    fun preparePopularItemsRecyclerView(){
        binding.recViewMealsPopular.apply {
            // set the layoutManager
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=popularItemAdapter
        }
    }

    fun observeCategories(){
        homeViewModel.getCategories()
        homeViewModel.categoriesResponse.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setCategoryList(categoryMeals = ArrayList<Category>())
        })
    }

}