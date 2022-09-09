package com.example.therecipesecret.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.databinding.CategoryItemBinding
import com.squareup.picasso.Picasso

class FavoriteMealsAdapter :RecyclerView.Adapter<FavoriteMealsAdapter.FavoriteMealsAdapterViewHolder>() {

    inner class FavoriteMealsAdapterViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)
        // to enhance the recyclerView Peformance
        private val difUtil = object : DiffUtil.ItemCallback<Meal>(){

            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem.idMeal == newItem.idMeal
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
              return oldItem == newItem
            }

        }

    // to setup the items in the recyclerView
    val differ =AsyncListDiffer(this@FavoriteMealsAdapter,difUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int, ): FavoriteMealsAdapterViewHolder {

        return FavoriteMealsAdapterViewHolder(CategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Picasso.get()
            .load(meal.strMealThumb)
            .into(holder.binding.imgCategoty)

        holder.binding.tvCategoryName.text = meal.strMeal
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }
}