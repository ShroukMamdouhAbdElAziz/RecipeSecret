package com.example.therecipesecret.home.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.therecipesecret.common.model.Category
import com.example.therecipesecret.databinding.CategoryItemBinding

import com.squareup.picasso.Picasso


class CategoryAdapter(): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    lateinit var onItemClick:((Category)->Unit)

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

      Picasso.get()
           .load(categoryMeals[position].strCategoryThumb)
           .into(holder.binding.imgCategoty)

        holder.binding.tvCategoryName.text=categoryMeals[position].strCategory

        // handling on ItemClick

        holder.itemView.setOnClickListener {
            onItemClick.invoke(categoryMeals[position])
        }
    }


    override fun getItemCount(): Int {
        return categoryMeals.size
    }


    inner class CategoryViewHolder( var binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root)
}


