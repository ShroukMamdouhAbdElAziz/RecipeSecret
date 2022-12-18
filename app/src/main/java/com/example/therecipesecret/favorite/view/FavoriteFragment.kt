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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentFavoriteBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.favorite.viewmodel.FavoriteViewModel
import com.example.therecipesecret.favorite.viewmodel.FavoriteViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {
    lateinit var binding: FragmentFavoriteBinding
    lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var favAdapter: MealsAdapter


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

        // anonymous class . // specify the direction that recyclerView scroll to
        // add swipe direction in the constructor
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(

            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

            // if we will takeAction when scroll up or down
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


                // get the position of swipe Meal
                val position = viewHolder.absoluteAdapterPosition

                var favMeal = favAdapter.differ.currentList[position]

                favoriteViewModel.deleteMeal(favMeal)


                // after delete the Meal, show the snackbar
                Snackbar.make(requireView(), "Meal has been deleted", Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo", View.OnClickListener {
                            // once press on Undo , insert the meal again
                            favoriteViewModel.insertMeal(favMeal)
                        }
                    ).show()
            }

        }

        // attach the ItemTouchHelper to the RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorite)
    }

    private fun setUpRecyclerView() {
        favAdapter = MealsAdapter()
        binding.rvFavorite.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favAdapter
        }
    }

    private fun getViewModel() {
        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val favoriteViewModelFactory = FavoriteViewModelFactory(repository)
        favoriteViewModel =
            ViewModelProvider(this, favoriteViewModelFactory).get(FavoriteViewModel::class.java)
    }


    private fun observeFavoriteMeals() {
        favoriteViewModel.favoriteMeals
        favoriteViewModel.favoriteMeals.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Log.d("test", it.idMeal)

            }

            favAdapter.differ.submitList(it)
        })

    }


}