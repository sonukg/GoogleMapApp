package com.sonukg.theonepractical.apis

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.JsonObject
import com.sonukg.theonepractical.model.LocalPlaceModel
import com.sonukg.theonepractical.model.Place
import com.sonukg.theonepractical.utils.AppDatabase
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppRepository(private val context: Context) {
    val placeDao = AppDatabase.getDataBase(context).placeDao()
    val localPlaceRepository = LocalPlaceRepository(placeDao)

   suspend fun getPlaces(): MutableList<Place> {
        val places = mutableListOf<Place>()
        val response: JsonObject = WebCongif.userConfig.getPlaces("23.03744,72.566","distance","bakery", true, "AIzaSyB2Az9gVUzQULUc55xQD9AE7gj9Ni5hvJk")

       val responseJson = JSONObject(response.toString())
       if (responseJson.has("results")) {
           val resultArray = responseJson.optJSONArray("results")
           resultArray?.let { placeArray ->
               for (i in 0 until placeArray.length()) {
                   val place = Place().apply {
                       parseJson(resultArray[i] as JSONObject)
                   }
                   places.add(place)
               }
           }
       }
        return places
    }

    suspend fun insertPlaceToLocalDB(places: MutableList<Place>) {
        places.forEach {
            val localPlace = LocalPlaceModel(
                it.name,
                it.icon,
                it.vicinity,
                it.geometry?.location?.lat ?: 0.0,
                it.geometry?.location?.lng ?: 0.0
            )
            localPlaceRepository.insert(localPlace)
        }
    }

    suspend fun getPlacesFromLocalDB(): List<LocalPlaceModel> {
        return localPlaceRepository.getPlaces()
    }

    fun clearTable() {
        localPlaceRepository.clearTable()
    }
}