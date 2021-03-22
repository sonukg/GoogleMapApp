package com.sonukg.theonepractical.model

import org.json.JSONObject

data class Place(
    var geometry: Geometry? = null,
    var name: String? = null,
    var icon: String? = null,
    var vicinity: String? = null
) {
    fun parseJson(json: JSONObject?) {
        json?.let {
            geometry = Geometry().apply {
                parseGeo(it.optJSONObject(GEOMETRY))
            }
            name = it.optString(NAME)
            icon = it.optString(ICON)
            vicinity = it.optString(VICINITY)
        }
    }

    companion object {
        private const val NAME = "name"
        private const val ICON = "icon"
        private const val VICINITY = "vicinity"
        private const val GEOMETRY = "geometry"
    }
}

data class Geometry(
    var location: Location? = null
) {
    fun parseGeo(json: JSONObject?) {
        json?.let {
            location = Location().apply {
                parseLocation(it.optJSONObject(LOCATION))
            }
        }
    }

    companion object {
        const val LOCATION = "location"
    }
}

data class Location(
    var lat: Double?= null,
    var lng: Double?= null
) {
    fun parseLocation(json: JSONObject?) {
        json?.let {
            lat = it.optDouble(LAT)
            lng = it.optDouble(LNG)
        }
    }

    companion object {
        const val LAT = "lat"
        const val LNG = "lng"
    }
}