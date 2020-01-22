package com.tosh.dogbreeds.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.tosh.dogbreeds.R
import com.tosh.dogbreeds.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel : ListViewModel
    private val dogsListAdapter = DogsListAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewModel.refresh()

        dogsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.refresh().observe(this, Observer {
            it?.let {
                dogsList.visibility = VISIBLE
                dogsListAdapter.updateDogsList(it)
            }
        })

        viewModel.dogsLoadError.observe(this, Observer {
            it.let {
                listError.visibility = if(it) VISIBLE else GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it.let {
                loadingView.visibility  = if(it) VISIBLE else GONE
                if (it){
                    listError.visibility = GONE
                    dogsList.visibility = GONE
                }
            }
        })
    }
}
