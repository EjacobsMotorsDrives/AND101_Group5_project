package com.example.petapi

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class PetAdapter (private val petList: List<String>): RecyclerView.Adapter<PetAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val petImage: ImageView
        val pokemName: TextView
        val pokeHeight: TextView
        val pokeWeight: TextView


        init {
            // Find our RecyclerView item's ImageView for future use
            petImage = view.findViewById(R.id.pet_image)
            pokemName = view.findViewById(R.id.pokemon_name)
            pokeHeight = view.findViewById(R.id.pokemon_height)
            pokeWeight = view.findViewById(R.id.pokemon_weight)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView)
            .load(petList[position])
            .centerCrop()
            .into(holder.petImage)

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
                    Log.d("Rover", "response successful$json")
                    pokemonName = json.jsonObject.getString("name")
                    pokemonHeight = json.jsonObject.getInt("height").toString()
                    pokemonWeight = json.jsonObject.getInt("weight").toString()


                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    throwable: Throwable?
                ) {
                    Log.d("Rover Error", errorResponse)
                }
            }]

            holder.pokemName.text = pokemonName
            holder.pokeHeight.text = pokemonHeight + "m"
            holder.pokeWeight.text = pokemonWeight + "lb"

        }
    }
    override fun getItemCount() = petList.size


    }