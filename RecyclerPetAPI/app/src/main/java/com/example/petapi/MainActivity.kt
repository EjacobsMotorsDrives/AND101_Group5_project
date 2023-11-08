package com.example.petapi


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.petapi.R.*
import okhttp3.Headers

var pokemonName = ""
var pokemonHeight = ""
var pokemonWeight = ""
val id = (1..10).random()

class MainActivity : AppCompatActivity() {

    private lateinit var petList: MutableList<String>
    private lateinit var rvPets: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        rvPets = findViewById(R.id.pet_list)
        petList = mutableListOf()

        val layoutManager = LinearLayoutManager(this)
        rvPets.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        rvPets.addItemDecoration(itemDecoration)

        getDogImageURL()

    }

    private fun getDogImageURL() {

        val pokemonIds = listOf(1..10).random()


        for (id in pokemonIds) {
            val client = AsyncHttpClient()

            client["https://pokeapi.co/api/v2/pokemon/$id/", object :
                JsonHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    Log.d("Dog", "response successful$json")

                    val petImageArray =
                        json.jsonObject.getJSONObject("sprites").getString("front_default")

                    petList.add(petImageArray)

                    val adapter = PetAdapter(petList)
                    rvPets.adapter = adapter
                    rvPets.layoutManager = LinearLayoutManager(this@MainActivity)

                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Dog Error", errorResponse)
                }
            }]

        }
    }

}



