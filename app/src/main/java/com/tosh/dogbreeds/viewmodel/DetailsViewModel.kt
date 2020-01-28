package com.tosh.dogbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tosh.dogbreeds.model.DogBreed
import com.tosh.dogbreeds.model.DogDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application){

    val dog =  MutableLiveData<DogBreed>()


     fun fetchDogDetail(uuid: Int){
        launch {
            val dogDetails = DogDatabase(getApplication()).dogDao().getDog(uuid)

            dog.value = dogDetails
        }
    }
}