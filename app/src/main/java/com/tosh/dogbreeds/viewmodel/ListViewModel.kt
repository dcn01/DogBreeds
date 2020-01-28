package com.tosh.dogbreeds.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tosh.dogbreeds.model.DogBreed
import com.tosh.dogbreeds.model.DogDatabase
import com.tosh.dogbreeds.model.DogsApiService
import com.tosh.dogbreeds.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferenceHelper(getApplication())

    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var dogs = MutableLiveData<List<DogBreed>>()


    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            dogsApiService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        storeDogsLocally(it)
                    },
                    {
                        dogsLoadError.value = true
                        loading.value = false
                    }
                )
        )
    }

    private fun dogsRetrieved(dogsList: List<DogBreed>) {
        dogs.value = dogsList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list: List<DogBreed>) {
        launch {
            val dao = DogDatabase(getApplication()).dogDao()

            dao.deleteAllDogs()

            val result = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }

            dogsRetrieved(list)
        }

        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}