package co.edu.eam.retrofit.API

import co.edu.eam.retrofit.response.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getDogsByBreeds(@Url url: String): Response<DogsResponse>


}