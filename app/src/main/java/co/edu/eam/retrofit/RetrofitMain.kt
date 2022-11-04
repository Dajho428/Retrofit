package co.edu.eam.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitMain {

    val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
 //   fun getRetrofit():Retrofit{
//        return Retrofit.Builder().baseUrl("https://dog.ceo/api/breed/")
//            .addConverterFactory(GsonConverterFactory.create()).build()
//    }
}