package com.tosh.dogbreeds.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tosh.dogbreeds.model.DogBreed

class ListViewModel: ViewModel() {

    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(): MutableLiveData<List<DogBreed>>{
        var dogs = MutableLiveData<List<DogBreed>>()
        val dog1 = DogBreed("1", "German Shepherd", "12","breedGroup",
                "Security","nice","")
        val dog2 = DogBreed("2", "Mwitu", "20","breedGroup",
            "Security","nice","")
        val dog3 = DogBreed("3", "Dobby", "15","breedGroup",
            "Sleep","Sleep","")

        val dogList = arrayListOf<DogBreed>(dog1,dog2,dog3)

        dogs.value = dogList

        dogsLoadError.value = false
        loading.value = false

        return dogs


    }

}