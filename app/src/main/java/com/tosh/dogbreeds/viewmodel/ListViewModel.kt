package com.tosh.dogbreeds.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.tosh.dogbreeds.model.DogBreed
import com.tosh.dogbreeds.model.DogDatabase
import com.tosh.dogbreeds.model.DogsApiService
import com.tosh.dogbreeds.util.NotificationsHelper
import com.tosh.dogbreeds.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val dogsApiService = DogsApiService()
    private val disposable = CompositeDisposable()
    private var prefHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    var dogs = MutableLiveData<List<DogBreed>>()


    fun refresh() {
        val updateTime = prefHelper.getUpdateTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase(){
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)

            Toast.makeText(getApplication(), "Retrived from database", Toast.LENGTH_SHORT).show()
        }
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
                        Toast.makeText(getApplication(), "Retrieved from endpoint", Toast.LENGTH_SHORT).show()
                        NotificationsHelper(getApplication()).createNotification()
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
            while (i < list.size) {
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