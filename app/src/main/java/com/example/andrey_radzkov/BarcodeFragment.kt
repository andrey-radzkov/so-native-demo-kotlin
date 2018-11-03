package com.example.andrey_radzkov

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.common.api.CommonStatusCodes


class BarcodeFragment : Fragment() {


    private val RC_BARCODE_CAPTURE = 9001
    private lateinit var barcodeScannedValue: TextView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.activity_barcode_, container, false)


        this.activity!!.title="SupplyOn: Complaint creation"


        barcodeScannedValue = rootView.findViewById(R.id.barcodeScannedValue)

        barcodeScannedValue.setOnClickListener({
            val intent2 = Intent(activity, BarcodeScanActivity::class.java)

            startActivityForResult(intent2, RC_BARCODE_CAPTURE)
        })

        val btnSendToProso = rootView.findViewById<Button>(R.id.sendToProso)
        btnSendToProso.setOnClickListener({

            val handler = Handler()

            handler.postDelayed({
                val notificationBuilder = NotificationCompat.Builder(this.context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Complaint submission")
                        .setContentText("Lets image - complaint data was sent to PROSO ;)")
                        .setAutoCancel(true)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setVibrate(longArrayOf(150, 100, 150, 100))
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setLights(Color.RED, 3000, 3000)
                val resultIntent = Intent(this.context, NavigationActivity::class.java)


                val stackBuilder = TaskStackBuilder.create(this.context!!)
                // Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(NavigationActivity::class.java)
                // Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent)
                val resultPendingIntent = stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                )
                notificationBuilder.setContentIntent(resultPendingIntent)
                val mNotificationManager = ContextCompat.getSystemService(this.context!!, NotificationManager::class.java) as NotificationManager
                // mId allows you to update the notification later on.
                val id = 1
                val notification = notificationBuilder.build()

                mNotificationManager.notify(id, notification)
            }, 3000)
        })

        return rootView
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
               if (data != null) {

                   val barcodeValue =data.getStringExtra("ScannedBarcodeValue")

                   barcodeScannedValue.setText(barcodeValue)

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
