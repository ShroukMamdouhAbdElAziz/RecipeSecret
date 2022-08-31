package com.example.therecipesecret.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.therecipesecret.common.model.CategoryMeal
import com.example.therecipesecret.databinding.PopularItemsRowLayoutBinding


class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    private var mealsList = ArrayList<CategoryMeal>()

    fun setMeal(mealsList:ArrayList<CategoryMeal>) {
        this.mealsList=mealsList
        //when we set the new List , refresh the adapter to update the view
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(PopularItemsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {

       Glide.with(holder.itemView)
           .load(mealsList[position].strMealThumb)
           .into(holder.binding.imgPopularMealItem)
    }


    override fun getItemCount(): Int {
        return mealsList.size
    }


    inner class PopularMealViewHolder( var binding: PopularItemsRowLayoutBinding):RecyclerView.ViewHolder(binding.root)
}