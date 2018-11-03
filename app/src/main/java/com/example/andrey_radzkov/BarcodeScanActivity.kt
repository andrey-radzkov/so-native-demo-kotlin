package com.example.andrey_radzkov

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class BarcodeScanActivity : AppCompatActivity() {

    private lateinit var svBarcode: SurfaceView
    private lateinit var tvBarcode: TextView

    private lateinit var barcodeDetector: BarcodeDetector
    private lateinit var cameraSource: CameraSource
    private lateinit var btnUseScannedCode: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        this.title = "SupplyOn: scan barcode"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        btnUseScannedCode = findViewById<Button>(R.id.emulateScanBarcode)

        btnUseScannedCode.setOnClickListener({
            val data = Intent()
            data.putExtra("ScannedBarcodeValue", if (tvBarcode.text.isEmpty()) "Tap here to scan product barcode!" else tvBarcode.text)
            setResult(CommonStatusCodes.SUCCESS, data)
            finish()
        })

        svBarcode = findViewById(R.id.sv_barcode)
        tvBarcode = findViewById(R.id.tv_barcode)

        barcodeDetector = BarcodeDetector.Builder(this).build()
        barcodeDetector.setProcessor(object: Detector.Processor<Barcode> {
            override fun release() {
                //do nothing
            }

            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                var barcodes = detections?.detectedItems
                if(barcodes!!.size() > 0) {
                    tvBarcode.post{
                        tvBarcode.text = barcodes.valueAt(0).displayValue
                        btnUseScannedCode.visibility = View.VISIBLE


                    }
                }
            }

        })

        cameraSource = CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1024, 768).setRequestedFps(25f).setAutoFocusEnabled(true).build()

        svBarcode.holder.addCallback(object: SurfaceHolder.Callback2 {
            override fun surfaceRedrawNeeded(holder: SurfaceHolder?) {
                //do nothing
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                //do nothing
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                cameraSource.stop()
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                if (ContextCompat.checkSelfPermission(this@BarcodeScanActivity, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraSource.start(holder)
                } else {
                    ActivityCompat.requestPermissions(this@BarcodeScanActivity, arrayOf(android.Manifest.permission.CAMERA), 123)
                }

            }

        })

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 123) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(svBarcode.holder)
            } else  {
                Toast.makeText(this, "Scanner will not work without permissions", Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        barcodeDetector.release()
        cameraSource.stop()
        cameraSource.release()
    }
}
