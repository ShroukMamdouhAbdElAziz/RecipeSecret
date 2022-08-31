package com.example.therecipesecret.categories.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.therecipesecret.R
import com.example.therecipesecret.databinding.FragmentCategoriesBinding
import com.example.therecipesecret.databinding.FragmentHomeBinding

class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,   savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment

        binding= FragmentCategoriesBinding.inflate(inflater,container,false)
        return binding.root
    }

}



