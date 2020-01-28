package com.tosh.dogbreeds.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.databinding.ItemDogBinding
import com.tosh.dogbreeds.model.DogBreed
import com.tosh.dogbreeds.util.getProgressDrawable
import com.tosh.dogbreeds.util.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(private val dogsList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(), DogClickListener{

    fun updateDogsList(newDogsList: List<DogBreed>) {
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DogViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_dog,
            parent,
            false
        )
    )

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this
    }

    override fun onDogClick(v: View) {
        val action = ListFragmentDirections.actioDetailFragment()
        action.dogUuid = v.dogId.text.toString().toInt()

        Navigation.findNavController(v).navigate(action)
    }
    class DogViewHolder(var view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)
}