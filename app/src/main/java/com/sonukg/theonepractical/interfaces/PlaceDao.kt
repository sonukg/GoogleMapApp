package com.sonukg.theonepractical.interfaces


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sonukg.theonepractical.model.LocalPlaceModel

@Dao
interface PlaceDao {
    @Query("SELECT * FROM tbl_place")
    fun getPlaces(): List<LocalPlaceModel>

    @Insert
    suspend fun insertPlace(place: LocalPlaceModel)

    @Query("DELETE FROM tbl_place")
    fun nukeTable()
}
