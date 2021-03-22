package com.sonukg.theonepractical.apis

import androidx.lifecycle.LiveData
import com.sonukg.theonepractical.interfaces.PlaceDao
import com.sonukg.theonepractical.model.LocalPlaceModel

class LocalPlaceRepository(private val placeDao: PlaceDao) {

    suspend fun getPlaces(): List<LocalPlaceModel> {
        return placeDao.getPlaces()
    }

    suspend fun insert(place: LocalPlaceModel){
        placeDao.insertPlace(place)
    }

    fun clearTable() {
        placeDao.nukeTable()
    }

}
