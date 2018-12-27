package com.example.andrey_radzkov

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.andrey_radzkov.service.NotificationService
import com.google.android.gms.common.api.CommonStatusCodes


class BarcodeFragment : Fragment() {


    private val RC_BARCODE_CAPTURE = 9001
    private lateinit var barcodeScannedLabel: TextView
    private lateinit var barcodeScannedCoordinate: TextView
    private lateinit var complaintImage: ImageView
    private var notificationService: NotificationService = NotificationService()

    // Storage Permissions
    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_barcode, container, false)
        this.activity!!.title = "Complaint in three click"

        barcodeScannedLabel = rootView.findViewById(R.id.barcodeScannedLabel)
        barcodeScannedCoordinate = rootView.findViewById(R.id.barcodeScannedCoordinate)
        complaintImage = rootView.findViewById(R.id.complaintImage)

        val intent2 = Intent(activity, BarcodeScanActivity::class.java)
        startActivityForResult(intent2, RC_BARCODE_CAPTURE)

        val btnSendToProso: Button = rootView.findViewById(R.id.sendToProso)

        btnSendToProso.setOnClickListener({
            notificationService.sendDelayedHotification("Complaint submission",
                    "Lets image - complaint data was sent to PROSO ;)", this.context!!, NavigationActivity::class.java)
        })

        return rootView
    }


    @SuppressLint("MissingPermission")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    verifyStoragePermissions(activity!!)

                    val locationManager: LocationManager? = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
                    val lastKnownLocation = locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                    val barcodeValue = data.getStringExtra("ScannedBarcodeValue")
                    barcodeScannedLabel.text = "Scanned code: $barcodeValue"
                    if (lastKnownLocation != null && lastKnownLocation.longitude != null && lastKnownLocation.latitude != null) {
                        barcodeScannedCoordinate.text = "Coordinate: " + lastKnownLocation.longitude + ", " + lastKnownLocation.latitude
                    } else {
                        barcodeScannedCoordinate.text = ""
                    }
                    val imageUri: Uri = data.getParcelableExtra("ImageBitmap")
                    val options = BitmapFactory.Options()
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888
                    val imageBitmap = BitmapFactory.decodeFile(imageUri.path, options)
                    complaintImage.setImageBitmap(imageBitmap)

                } else {
                    barcodeScannedLabel.setText("Empty data came from barcode")
                }
            } else {
                barcodeScannedLabel.setText("Error occured. Feel free to debug")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            )
        }
    }


}
