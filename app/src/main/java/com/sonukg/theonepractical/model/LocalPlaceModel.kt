package com.sonukg.theonepractical.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_place")
data class LocalPlaceModel (
    @SerializedName("name")var name: String? = null,
    @SerializedName("icon")var icon: String? = null,
    @SerializedName("vicinity")var vicinity: String? = null,
    @SerializedName("lat")var lat: Double = 0.0,
    @SerializedName("lng")var lng: Double = 0.0,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}