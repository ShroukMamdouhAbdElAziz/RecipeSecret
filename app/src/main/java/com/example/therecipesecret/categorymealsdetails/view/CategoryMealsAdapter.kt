package com.example.therecipesecret.categorymealsdetails.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.model.CategoryMealsDetails
import com.example.therecipesecret.databinding.CategoryItemBinding
import com.example.therecipesecret.databinding.CategoryMealsItemBinding
import com.squareup.picasso.Picasso


class CategoryMealsAdapter(): RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewHolder>() {


    private var categoryMealsList = ArrayList<CategoryMealsDetails>()

    fun setCategoryMeals(categoryMealsList:ArrayList<CategoryMealsDetails>) {
        this.categoryMealsList=categoryMealsList
        //when we set the new List , refresh the adapter to update the view
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
        return CategoryMealsViewHolder(CategoryMealsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {

        Picasso.get().load(categoryMealsList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text= categoryMealsList[position].strMeal

    }


    override fun getItemCount(): Int {
        return categoryMealsList.size
    }


    inner class CategoryMealsViewHolder( var binding:CategoryMealsItemBinding):RecyclerView.ViewHolder(binding.root)
}