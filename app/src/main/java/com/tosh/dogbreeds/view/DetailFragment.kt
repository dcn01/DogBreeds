package com.tosh.dogbreeds.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation

import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private var dogUUID = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            dogUUID = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.fetch().observe(this, Observer {
            it?.let {
                dogName.text = it.dogBreed
                dogPurpose.text = it.bredFor
                dogTemperament.text = it.temperament
                dogLifespan.text = it.lifespan
                
            }
        })
    }
}
