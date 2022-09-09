package com.example.therecipesecret.bottomsheet.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.R
import com.example.therecipesecret.bottomsheet.viewmodel.MealBottomSheetViewModel
import com.example.therecipesecret.bottomsheet.viewmodel.MealBottomSheetViewModelFactory
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentMealBottomSheetBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.home.view.HomeFragment
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory
import com.example.therecipesecret.mealdetails.view.MealActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

private const val MEAL_ID = "param1"

class MealBottomSheet : BottomSheetDialogFragment() {

    private var mealName: String? = null
    private var mealThumb: String? = null

    lateinit var binding: FragmentMealBottomSheetBinding
    lateinit var bottomSheetViewModel: MealBottomSheetViewModel


    private var mealId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)

            getViewModel()

        }
    }


    fun getViewModel() {

        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val bottomSheetViewModelFactory = MealBottomSheetViewModelFactory(repository)
        bottomSheetViewModel = ViewModelProvider(this, bottomSheetViewModelFactory).get(
            MealBottomSheetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMealDeatils()
        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {
        binding.bottomSheet.setOnClickListener {
            if (mealName != null && mealThumb != null) {

                val intent = Intent(activity, MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID, mealId)
                    putExtra(HomeFragment.MEAL_NAME, mealName)
                    putExtra(HomeFragment.MEAL_THUMB, mealThumb)

                }
                startActivity(intent)
            }
        }
    }


    private fun observeMealDeatils() {
        mealId?.let { bottomSheetViewModel.getRandomMealInformation(it) }
        bottomSheetViewModel.myRespone.observe(viewLifecycleOwner, Observer {
            var meal = it.meals[0]
            Picasso.get().load(meal.strMealThumb)
                .into(binding.imgBottomSheet)

            binding.tvBottomsheetArea.text = meal.strArea
            binding.tvBottomsheetCategory.text = meal.strCategory
            binding.bottomsheetMealName.text = meal.strMeal
            mealName = meal.strMeal
            mealThumb = meal.strMealThumb

        })
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1)

                }
            }
    }
}