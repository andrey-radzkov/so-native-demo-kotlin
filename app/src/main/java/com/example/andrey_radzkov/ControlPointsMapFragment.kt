package com.example.andrey_radzkov

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.andrey_radzkov.service.Constants
import com.example.andrey_radzkov.service.GeofenceTransitionsIntentService
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import java.util.Locale


/**
 * @author Radzkov Andrey
 */
class ControlPointsMapFragment : Fragment(), OnMapReadyCallback {
    companion object {
        val TAG: String = "MainActivity"
        private val REQ_PERMISSION: Int = 100
    }

    var mMapView: MapView? = null
    var geocoder: Geocoder? = null
    private lateinit var mMap: GoogleMap
    private lateinit var mGeofenceList: ArrayList<Geofence>

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_control_ponts_map, container, false)
        mMapView = rootView.findViewById(R.id.map)
        mMapView!!.onCreate(savedInstanceState)
        mMapView!!.getMapAsync(this)
        geocoder = Geocoder(context, Locale.getDefault())
        mGeofenceList = ArrayList()

        return rootView
    }

    private fun checkPermission(): Boolean {
        Log.d(TAG, "===============> checkPermission()")
        // Ask for permission if it wasn't granted yet
        return ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // Asks for permission
    private fun askPermission() {
        Log.d(TAG, "===============> askPermission()")
        ActivityCompat.requestPermissions(activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
    }

    private fun addGeofencesHandler() {
        if ((ActivityCompat.checkSelfPermission(context!!,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) || mGeofenceList.isEmpty()) {
            return
        }
        LocationServices.getGeofencingClient(context!!).addGeofences(getGeofencingRequest(),
                getGeofencePendingIntent())
    }

    private fun getGeofencingRequest(): GeofencingRequest {
        Log.d(TAG, "===============> getGeofencingRequest()")
        val builder = GeofencingRequest.Builder()
//        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_DWELL)
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        builder.addGeofences(mGeofenceList)
        return builder.build()
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        Log.d(TAG, "===============> getGeofencePendingIntent()")
        val intent = Intent(context, GeofenceTransitionsIntentService::class.java)
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling addgeoFences()
        return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = "Control points"
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val handler = Handler()
        handler.postDelayed({
            val goretskogo = "ул Горецкого 51, Минск"
            val sharangovicha = "ул Шаранговича 52, Минск"
            val zhukova = "ул Жукова 29, Минск"
            val supplyon = "Ludwigstraße 49, 85399 Hallbergmoos, Германия"


            val minskSharangovicha = getCoordinateByAddress(sharangovicha)
            val minskZhukova = getCoordinateByAddress(zhukova)
            val minskGoretskogo = getCoordinateByAddress(goretskogo)
            val germanySupplyon = getCoordinateByAddress(supplyon)

            if (minskSharangovicha != null && minskZhukova != null) {
                mMap.addPolyline(PolylineOptions().add(minskSharangovicha, minskZhukova)
                        .width(4F)
                        .color(Color.RED))
            }
            if (minskGoretskogo != null && minskZhukova != null) {
                mMap.addPolyline(PolylineOptions().add(minskGoretskogo, minskZhukova)
                        .width(4F)
                        .color(Color.BLUE))
            }
            if (germanySupplyon != null && minskZhukova != null) {
                mMap.addPolyline(PolylineOptions().add(germanySupplyon, minskZhukova)
                        .width(4F)
                        .color(Color.BLUE))
            }
            minskSharangovicha?.let {
                mMap.addMarker(MarkerOptions().position(minskSharangovicha).title(sharangovicha))
                mGeofenceList.add(getGeofence(sharangovicha, minskSharangovicha))
            }
            minskZhukova?.let {
                mMap.addMarker(MarkerOptions().position(minskZhukova).title(zhukova)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.epam_logo, 96, 96))))
                mGeofenceList.add(getGeofence("Epam, Minsk", minskZhukova))
            }
            minskGoretskogo?.let {
                mMap.addMarker(MarkerOptions().position(minskGoretskogo).title(goretskogo))
                mGeofenceList.add(getGeofence("Home", minskGoretskogo))
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minskGoretskogo, 11.0f))
            }
            germanySupplyon?.let {
                mMap.addMarker(MarkerOptions().position(germanySupplyon).title(supplyon)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(R.drawable.so_logo, 96, 96))))

                mGeofenceList.add(getGeofence("SupplyOn AG", germanySupplyon))
            }


            if (checkPermission()) {
                addGeofencesHandler()
            } else {
                askPermission()
            }
        }, 200)
        mMapView!!.onResume()
    }

    private fun getGeofence(name: String, coordinate: LatLng): Geofence {
        return Geofence.Builder().setRequestId(name)
                .setCircularRegion(coordinate.latitude, coordinate.longitude, Constants.GEOFENCE_RADIUS_IN_METERS)
                .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                .setNotificationResponsiveness(3000)//TODO: analyse
                .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
    }

    fun getCoordinateByAddress(address: String): LatLng? {
        try {
            val locationsByAddress = geocoder!!.getFromLocationName(address, 1)
            if (locationsByAddress != null && locationsByAddress.isNotEmpty()) {
                val foundAddress = locationsByAddress[0]
                val addressCoordinate = LatLng(foundAddress.latitude, foundAddress.longitude)
                return addressCoordinate
            }
        } catch (e: Exception) {
            println("no internet")
        }
        return null
    }

    fun resizeMapIcons(id: Int, width: Int, height: Int): Bitmap {
        val imageBitmap: Bitmap = BitmapFactory.decodeResource(getResources(), id)
        val resizedBitmap: Bitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false)
        return resizedBitmap;
    }

}