package com.example.therecipesecret.search.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.therecipesecret.common.model.MealList
import com.example.therecipesecret.common.repository.Repository
import com.example.therecipesecret.common.retrofit.RetrofitInstance
import com.example.therecipesecret.databinding.FragmentSearchBinding
import com.example.therecipesecret.db.MealDataBase
import com.example.therecipesecret.favorite.view.MealsAdapter
import com.example.therecipesecret.home.viewmodel.HomeViewModel
import com.example.therecipesecret.home.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchRecyclerViewAdapter: MealsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareSearchRecyclerView()
        onSearchArrowClick()
        observeSearchMealsLiveData()
       runSearchAutomatically()


    }

    private fun runSearchAutomatically() {
        // to execute the search automatically
        //  when the user finished entering the query without clicking on arrow
        // any tye the entered text will be changed the job will be cancelled and start a new one (coroutine for the new job)
        var searchJob : Job?=null
        // it refers to the search query
        binding.editTextSearchBox.addTextChangedListener {
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.getMealBySearch(it.toString())
            }
        }
    }

    private fun onSearchArrowClick() {
        binding.searchArrowBtn.setOnClickListener {
            searchMeals()
        }
    }

    private fun searchMeals() {
        val searchQuery = binding.editTextSearchBox.text.toString()
        if (searchQuery.isNotEmpty()) {
            viewModel.getMealBySearch(searchQuery)
        }
    }

    private fun observeSearchMealsLiveData() {

        viewModel.observerSearchMealLiveData().observe(viewLifecycleOwner, Observer {
          searchRecyclerViewAdapter.differ.submitList()



        })
    }

    private fun getViewModel() {

        val mealDao = MealDataBase.getDataBaseInstance(requireContext()).getMealDao()
        val mealApi = RetrofitInstance.api

        val repository = Repository(mealDao, mealApi)

        val homeViewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel::class.java)
    }

    private fun prepareSearchRecyclerView() {
        searchRecyclerViewAdapter = MealsAdapter()
        binding.searchRecycledView.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = searchRecyclerViewAdapter


        }

    }




}




