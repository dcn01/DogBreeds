package com.tosh.dogbreeds.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.model.DogBreed
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<DogBreed>): RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

    fun updateDogsList(newDogsList: List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DogViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_dog, parent, false)
    )

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogsList[position])
    }

    class DogViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private val dogName = view.name
        private val dogLifespan = view.lifespan


        fun bind(dog: DogBreed){
            dogName.text = dog.dogBreed
            dogLifespan.text = dog.lifespan

            view.setOnClickListener {

                Navigation.findNavController(it).navigate(ListFragmentDirections.actioDetailFragment())
            }
        }
    }
}