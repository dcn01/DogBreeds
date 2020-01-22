package com.tosh.dogbreeds.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tosh.dogbreeds.model.DogBreed

class DetailsViewModel : ViewModel(){


    fun fetch(): MutableLiveData<DogBreed>{
        val dogLiveData =  MutableLiveData<DogBreed>()
        val dog = DogBreed("1", "German Shepherd", "12","breedGroup",
            "Security","nice","")

        dogLiveData.value = dog

        return dogLiveData
    }
}