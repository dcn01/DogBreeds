package com.tosh.dogbreeds.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.util.getProgressDrawable
import com.tosh.dogbreeds.util.loadImage
import com.tosh.dogbreeds.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var viewModel: DetailsViewModel
    private var dogUUID = 0
    
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
                dogName.text = it.dogBreed
                dogPurpose.text = it.bredFor
                dogTemperament.text = it.temperament
                dogLifespan.text = it.lifespan

                context?.let{
                    dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))
                }
            }
        })
    }
}
