package com.example.therecipesecret.favorite.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.R
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentFavoriteBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.favorite.viewmodel.FavoriteViewModel
import com.example.therecipesecret.favorite.viewmodel.FavoriteViewModelFactory
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory


class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favAdapter:FavoriteMealsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getViewModel()


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        observeFavoriteMeals()
    }

    private fun setUpRecyclerView() {
        favAdapter= FavoriteMealsAdapter()
        binding.rvFavorite.apply {
           layoutManager= GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter= favAdapter
        }
    }

    private fun getViewModel() {
        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val favoriteViewModelFactory = FavoriteViewModelFactory(repository)
        favoriteViewModel = ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }



    private fun observeFavoriteMeals() {
        favoriteViewModel.getAllMeals()
        favoriteViewModel.favoriteMeals.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Log.d("test",it.idMeal)

            }

            favAdapter.differ.submitList(it)
        })

    }


}