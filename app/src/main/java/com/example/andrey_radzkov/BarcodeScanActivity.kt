package com.example.andrey_radzkov

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
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
    private lateinit var scannedText: String
    val REQUEST_IMAGE_CAPTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        this.title = "SupplyOn: scan barcode"

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode_scan)

        svBarcode = findViewById(R.id.sv_barcode)
        tvBarcode = findViewById(R.id.tv_barcode)

        barcodeDetector = BarcodeDetector.Builder(this).build()
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                //do nothing
            }

            @SuppressLint("MissingPermission")
            override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
                val barcodes = detections?.detectedItems
                if (barcodes!!.size() > 0) {
                    vibrate()
                    tvBarcode.post {
                        tvBarcode.text = barcodes.valueAt(0).displayValue
                        dispatchTakePictureIntent()
                        scannedText = tvBarcode.text!! as String
                        cameraSource.stop()
                    }

                }
            }

        })

        cameraSource = CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1024, 768).setRequestedFps(25f).setAutoFocusEnabled(true).build()

        svBarcode.holder.addCallback(object : SurfaceHolder.Callback2 {
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

    private fun vibrate() {
        val vibrator: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect: VibrationEffect = VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE);
            vibrator.vibrate(effect)
        } else {
            vibrator.vibrate(150)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 123) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraSource.start(svBarcode.holder)
            } else {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            vibrate()
            val resultIntent = Intent()
            resultIntent.putExtra("ScannedBarcodeValue", scannedText)
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            resultIntent.putExtra("ImageBitmap", imageBitmap)
            setResult(CommonStatusCodes.SUCCESS, resultIntent)
            finish()
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra("android.intent.extra.quickCapture", true)
        intent.also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
