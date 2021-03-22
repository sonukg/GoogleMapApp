package com.sonukg.theonepractical.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.sonukg.theonepractical.R
import com.sonukg.theonepractical.model.LocalPlaceModel
import com.sonukg.theonepractical.viewmodel.MainActivityViewModel
import com.sonukg.theonepractical.viewmodel.factory.ViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
    GoogleMap.OnInfoWindowClickListener {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mMap: GoogleMap
    private var placeList = arrayListOf<LocalPlaceModel>()
    var search_name:EditText?=null
    lateinit var placelist:List<Place.Field>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        search_name=findViewById(R.id.search_name)
        initializeUI()
        initializeViewModel()
        viewModel.placesFromLocalDB().observe(this, Observer {
            addMarkersOnMap(it)
        })

        Places.initialize(this,"AIzaSyBjzdwmdeLRdpYKxDHLgTWc3kRVXABYFIU")
        search_name!!.isFocusable=false
        search_name!!.setOnClickListener {
             placelist=Arrays.asList(Place.Field.NAME)
            val intent:Intent=Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                placelist).build(this)
            startActivityForResult(intent,100)
        }
    }

    private fun addMarkersOnMap(places: List<LocalPlaceModel>?) {
        places?.forEachIndexed { index, place ->
            placeList.add(place)
            // Adding markers
            val location = LatLng(place.lat, place.lng)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(place.name)
                    .snippet(place.vicinity)
            )
            marker.tag = index
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
        }
    }

    private fun initializeUI() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun initializeViewModel() {
        val factory = ViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMarkerClickListener(this)
        mMap.setOnInfoWindowClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        return false
    }

    override fun onInfoWindowClick(marker: Marker?) {
        val position = marker?.tag as Int
        val place = placeList[position]
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=${place.lat},${place.lng}")
            )
        )
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode== RESULT_OK){
            val place:Place=Autocomplete.getPlaceFromIntent(data!!)
            //search_name!!.setText(place.name)
        }
        else if(resultCode==AutocompleteActivity.RESULT_ERROR){
            val status:Status=Autocomplete.getStatusFromIntent(data!!)
            Toast.makeText(this,status.statusMessage,Toast.LENGTH_SHORT).show()
        }
    }

}