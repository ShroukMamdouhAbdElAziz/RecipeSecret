package com.example.therecipesecret.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.model.Meal
import com.example.therecipesecret.databinding.FavMealItemBinding
import com.squareup.picasso.Picasso
// used for favorite meals adapter and search adapter as they have the same layouts

class MealsAdapter :RecyclerView.Adapter<MealsAdapter.FavoriteMealsAdapterViewHolder>() {

    inner class FavoriteMealsAdapterViewHolder(val binding: FavMealItemBinding):RecyclerView.ViewHolder(binding.root)
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
    val differ =AsyncListDiffer(this@MealsAdapter,difUtil)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int, ): FavoriteMealsAdapterViewHolder {

        return FavoriteMealsAdapterViewHolder(FavMealItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteMealsAdapterViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Picasso.get()
            .load(meal.strMealThumb)
            .into(holder.binding.imgFavMeal)

        holder.binding.tvFavMealName.text= meal.strMeal
    }

    override fun getItemCount(): Int {
        return  differ.currentList.size
    }
}