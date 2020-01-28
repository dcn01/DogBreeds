package com.tosh.dogbreeds.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.databinding.FragmentDetailBinding
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
            }
        })
    }
}
