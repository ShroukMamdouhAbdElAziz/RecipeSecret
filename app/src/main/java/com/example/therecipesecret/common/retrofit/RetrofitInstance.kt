package com.example.therecipesecret.common.retrofit

import com.example.therecipesecret.common.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:MealApi by lazy {
        retrofit.create(MealApi::class.java)
    }



}
/*class RetrofitInstance{
    companion object {

         var api: MealApi? = null

        fun getRetrofitInstance(): MealApi {
            if (api == null) {
                var retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                api = retrofit.create(MealApi::class.java)
            }
            return api!!

        }

    }
}*/