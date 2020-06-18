package com.shurjomukhi.shurjopay.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import com.shurjomukhi.shurjopay.R
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

  private lateinit var mScannerView: ZXingScannerView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    mScannerView = ZXingScannerView(this)
    mScannerView.setLaserEnabled(false)
    setContentView(mScannerView)
  }

  override fun onResume() {
    super.onResume()
    mScannerView.setResultHandler(this) // Register ourselves as a handler for scan results.
    mScannerView.startCamera() // Start camera on resume
    //mScannerView.flash = true
  }

  override fun onPause() {
    super.onPause()
    mScannerView.stopCamera() // Stop camera on pause
  }

  override fun handleResult(rawResult: Result) {
    Log.v(TAG, rawResult.text) // Prints scan results
    Log.v(TAG, rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode, pdf417 etc.)
    Toast.makeText(this, rawResult.text, Toast.LENGTH_SHORT).show()

    // If you would like to resume scanning, call this method below:
    //mScannerView.resumeCameraPreview(this)
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
  }

  companion object {
    private const val TAG = "ScannerActivity"
  }
}