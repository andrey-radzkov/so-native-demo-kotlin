package com.example.andrey_radzkov

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
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
    private lateinit var barcodeScannedValue: TextView
    private lateinit var complaintImage: ImageView
    private var notificationService: NotificationService = NotificationService()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_barcode, container, false)
        this.activity!!.title = "Complaint in three click"

        barcodeScannedValue = rootView.findViewById(R.id.barcodeScannedValue)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {

                    val barcodeValue = data.getStringExtra("ScannedBarcodeValue")
                    barcodeScannedValue.setText(barcodeValue)
                    val imageValue: Bitmap = data.getParcelableExtra("ImageBitmap")
                    complaintImage.setImageBitmap(imageValue)
                } else {
                    barcodeScannedValue.setText("Empty data came from barcode")
                }
            } else {
                barcodeScannedValue.setText("Error occured. Feel free to debug")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }


    }


}
