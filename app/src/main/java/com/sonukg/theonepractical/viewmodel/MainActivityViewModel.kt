package com.sonukg.theonepractical.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.sonukg.theonepractical.R
import com.sonukg.theonepractical.apis.AppRepository
import com.sonukg.theonepractical.model.LocalPlaceModel
import com.sonukg.theonepractical.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(context: Context): ViewModel() {
    private var repository: AppRepository = AppRepository(context)
    private val localPlacesList = MutableLiveData<List<LocalPlaceModel>>()
    private var serverPlacesList: MutableList<Place> = mutableListOf()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            serverPlacesList =  repository.getPlaces()
           launch(Dispatchers.Main) {
               if (serverPlacesList.isNotEmpty()) {
                   addPlacesToLocalDB(serverPlacesList)
               }
           }
        }
    }

    private fun addPlacesToLocalDB(places: MutableList<Place>) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.clearTable()
            if (places.isNotEmpty()){
                repository.insertPlaceToLocalDB(places)
                fetchLocalPlaces()
            }
        }
    }

    private fun fetchLocalPlaces() {
        GlobalScope.launch(Dispatchers.IO) {
            val result = repository.getPlacesFromLocalDB()
            launch(Dispatchers.Main) {
                localPlacesList.value = result
            }
        }
    }

    fun placesFromLocalDB(): LiveData<List<LocalPlaceModel>> {
        return localPlacesList
    }


}