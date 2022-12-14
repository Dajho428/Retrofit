package co.edu.eam.retrofit

import co.edu.eam.retrofit.API.APIService
import org.junit.Test

import retrofit2.Retrofit
import strikt.api.*


class ExampleUnitTest {
    val instance: Retrofit = RetrofitMain().retrofit
    val service = instance.create(APIService::class.java)

    @Test
    suspend fun comprobarEstadoRespuesta(){
        val call = service.getDogsByBreeds("collie/images")
        expectThat(call.body()!!.status){
            assertThat("Exito"){
                it == "success"
            }
        }
        }
    }


