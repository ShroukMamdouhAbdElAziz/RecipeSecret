package com.example.therecipesecret.mealdetails.view


import android.content.Intent
import android.icu.text.Transliterator
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.therecipesecret.R
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.ActivityMealBinding
import com.example.therecipesecret.databinding.CustomToastBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.favorite.viewmodel.FavoriteViewModel
import com.example.therecipesecret.home.view.HomeFragment
import com.example.therecipesecret.mealdetails.viewmodel.MealDetailsViewModel
import com.example.therecipesecret.mealdetails.viewmodel.MealDetailsViewModelFactory
import com.squareup.picasso.Picasso


class MealActivity : AppCompatActivity() {

    lateinit var binding: ActivityMealBinding

    //  lateinit var customToastBinding : CustomToastBinding
    lateinit var mealDetailsViewModel: MealDetailsViewModel


    lateinit var mealId: String
    lateinit var mealName: String
    lateinit var mealThumb: String

    lateinit var youtubeLink: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)


        setContentView(binding.root)

        getMealDetailsFromIntent()
        getViewModel()

        setInformationInView()
        loadingCase()

        observeMealDeatils()
        onYoutubeImageClick()

        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.favBtn.setOnClickListener {
            favMeal?.let {
                mealDetailsViewModel.insertMeal(it)
                Log.d("TestFav", favMeal.toString())

                Toast.makeText(this,
                    "the meal has been added to your Favorite list",
                    Toast.LENGTH_LONG).show()


            }
        }

    }


    private fun getViewModel() {
        val mealDao = MealDataBase.getDataBaseInstance(this).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val mealDetailsViewModelFactory = MealDetailsViewModelFactory(repository)

        mealDetailsViewModel = ViewModelProvider(this,
            mealDetailsViewModelFactory).get(MealDetailsViewModel::class.java)
    }


    private fun getMealDetailsFromIntent() {
        val intent = getIntent()
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private fun setInformationInView() {
        Picasso.get()
            .load(mealThumb)
            .into(binding.imgMealDetails)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

        // when the too;bar collapsed, the title will be in white color
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))

    }


    private var favMeal: Meal? = null

    private fun observeMealDeatils() {
        mealDetailsViewModel.getRandomMealInformation(mealId)
        mealDetailsViewModel.myRespone.observe(this, Observer {
            responseCase()
            favMeal = it.meals[0]
            binding.tvArea.text = "Area : ${it.meals[0].strArea}"
            binding.tvCategory.text = "Category : ${it.meals[0].strCategory}"
            binding.instructionTxtViewSteps.text = it.meals[0].strInstructions
            youtubeLink = it.meals[0].strYoutube!!
        })
    }


    // show the progress bar when the data is still loading from the API and hiding all the views
    private fun loadingCase() {
        binding.favBtn.visibility = View.INVISIBLE
        binding.instructionTxtViewSteps.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    // display all the views on the screen once the api response is done
    private fun responseCase() {
        binding.favBtn.visibility = View.VISIBLE
        binding.instructionTxtViewSteps.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)

        }
    }


}
