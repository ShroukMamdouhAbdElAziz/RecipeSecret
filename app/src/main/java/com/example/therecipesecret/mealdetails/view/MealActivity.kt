package com.example.therecipesecret.mealdetails.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.therecipesecret.R
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.databinding.ActivityMealBinding
import com.example.therecipesecret.home.view.HomeFragment
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory
import com.example.therecipesecret.mealdetails.viewmodel.MealDetailsViewModel
import com.example.therecipesecret.mealdetails.viewmodel.MealDetailsViewModelFactory
import com.squareup.picasso.Picasso
import retrofit2.http.Url

class MealActivity : AppCompatActivity() {

    lateinit var binding: ActivityMealBinding
    lateinit var mealDetailsViewModel: MealDetailsViewModel

    lateinit var mealId:String
    lateinit var mealName:String
    lateinit var mealThumb:String

    lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        val mealDetailsViewModelFactory = MealDetailsViewModelFactory(repository)
        mealDetailsViewModel = ViewModelProvider(this,
            mealDetailsViewModelFactory).get(MealDetailsViewModel::class.java)

        getMealDetailsFromIntent()
        setInformationInView()
        loadingCase()

        observeMealDeatils()
        onYoutubeImageClick()

    }


    private fun getMealDetailsFromIntent(){
        val intent= getIntent()
        mealId= intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName= intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb=intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun setInformationInView(){
        Picasso.get()
            .load(mealThumb)
            .into(binding.imgMealDetails)

        binding.collapsingToolBar.title=mealName
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

        // when the too;bar collapsed, the title will be in white color
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))

    }

    private fun observeMealDeatils(){
        mealDetailsViewModel.getRandomMealInformation(mealId)
        mealDetailsViewModel.myRespone.observe(this, Observer { response->
            responseCase()
            binding.tvArea.text = "Area : ${response.meals[0].strArea}"
            binding.tvCategory.text= "Category : ${response.meals[0].strCategory}"
            binding.instructionTxtViewSteps.text =response.meals[0].strInstructions
            youtubeLink=response.meals[0].strYoutube
        })
    }

    // show the progress bar when the data is still loading from the API and hiding all the views
    private fun loadingCase(){
        binding.favBtn.visibility=View.INVISIBLE
        binding.instructionTxtViewSteps.visibility=View.INVISIBLE
        binding.tvArea.visibility=View.INVISIBLE
        binding.tvCategory.visibility=View.INVISIBLE
        binding.imgYoutube.visibility=View.INVISIBLE
    }

    // display all the views on the screen once the api response is done
    private fun responseCase(){
        binding.favBtn.visibility=View.VISIBLE
        binding.instructionTxtViewSteps.visibility=View.VISIBLE
        binding.tvArea.visibility=View.VISIBLE
        binding.tvCategory.visibility=View.VISIBLE
        binding.imgYoutube.visibility=View.VISIBLE
    }

    fun onYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent =Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)

        }
    }



}
