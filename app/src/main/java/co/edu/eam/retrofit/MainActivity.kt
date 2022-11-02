package co.edu.eam.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.eam.retrofit.API.APIService
import co.edu.eam.retrofit.adapter.DogAdapter
import co.edu.eam.retrofit.databinding.ActivityMainBinding
import co.edu.eam.retrofit.response.DogsResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogImages = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.searchViewDogs.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter= DogAdapter(dogImages)
        binding.recyclerViewDogs.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewDogs.adapter =  adapter
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    private fun searchByName(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<DogsResponse> = getRetrofit().create(APIService::class.java).getDogsByBreeds("$query/images")
            val puppies: DogsResponse?=call.body()
            runOnUiThread{
                if(call.isSuccessful){
                    val images = puppies?.images?: emptyList()
                    dogImages.clear()
                    dogImages.addAll(images)
                    adapter.notifyDataSetChanged()
                }else{
                    showError()
                }
                hideKb()
            }

        }
    }

    private fun hideKb() {
        val imm=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken,0)
    }

    private fun showError() {
        Toast.makeText(this,"No tenemos ninguna raza de perro asosciada a tu busqueda",Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByName(query.toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}