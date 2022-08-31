package com.example.therecipesecret.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therecipesecret.common.model.Category
import com.example.therecipesecret.databinding.CategoryItemBinding


class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryMeals = ArrayList<Category>()

    fun setCategoryList(categoryMeals:ArrayList<Category>) {
        this.categoryMeals=categoryMeals
        //when we set the new List , refresh the adapter to update the view
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

       Glide.with(holder.itemView)
           .load(categoryMeals[position].strCategoryThumb)
           .into(holder.binding.imgCategoty)

        holder.binding.tvCategoryName.text=categoryMeals[position].strCategory
    }


    override fun getItemCount(): Int {
        return categoryMeals.size
    }


    inner class CategoryViewHolder( var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)
}


