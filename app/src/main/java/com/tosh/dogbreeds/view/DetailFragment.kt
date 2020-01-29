package com.tosh.dogbreeds.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.databinding.FragmentDetailBinding
import com.tosh.dogbreeds.model.DogPalette
import com.tosh.dogbreeds.viewmodel.DetailsViewModel

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private var dogUUID = 0
    private lateinit var databinding: FragmentDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.let {
            dogUUID = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

        viewModel.fetchDogDetail(dogUUID)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.dog.observe(this, Observer {dog->
            dog?.let {
                databinding.dogDetails = dog

                it.imageUrl?.let {
                    setupBackgroundColor(it)
                }
            }
        })
    }

    private fun setupBackgroundColor(url: String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object: CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource).generate{palette ->  
                        val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                        val myPalette = DogPalette(intColor)

                        databinding.palette = myPalette
                    }
                }

            })
    }
}
