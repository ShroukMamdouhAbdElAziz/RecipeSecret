package com.example.therecipesecret.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.model.Category


import com.example.therecipesecret.common.model.PopularMeals
import com.example.therecipesecret.databinding.PopularItemsRowLayoutBinding
import com.squareup.picasso.Picasso



class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {

    // You can use a lambda as the click callback, e.g. as a class property(method) of your adapter
    // the data type here(for the argument) is PopularMeals and the return is unit
    lateinit var onItemClick :((PopularMeals)->Unit)

    private var mealsList = ArrayList<PopularMeals>()

    fun setMeal(mealsList:ArrayList<PopularMeals>) {
        this.mealsList=mealsList
        //when we set the new List , refresh the adapter to update the view
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
       return PopularMealViewHolder(PopularItemsRowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {

        Picasso.get().load(mealsList[position].strMealThumb).into(holder.binding.imgPopularMealItem)

     // onitemclick handling
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }


    }


    override fun getItemCount(): Int {
        return mealsList.size
    }


    inner class PopularMealViewHolder( var binding: PopularItemsRowLayoutBinding):RecyclerView.ViewHolder(binding.root)
}