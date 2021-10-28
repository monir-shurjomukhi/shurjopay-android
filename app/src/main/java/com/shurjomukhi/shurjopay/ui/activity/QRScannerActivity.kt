package com.shurjomukhi.shurjopay.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.DnsResolver
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.*
import com.google.zxing.common.HybridBinarizer
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityQrScannerBinding
import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.networking.ApiClient
import com.shurjomukhi.shurjopay.networking.ApiInterface
import me.dm7.barcodescanner.zxing.ZXingScannerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class QRScannerActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

  private lateinit var mBinding: ActivityQrScannerBinding
  private lateinit var mScannerView: ZXingScannerView

  private lateinit var alertDialog: AlertDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mBinding = ActivityQrScannerBinding.inflate(layoutInflater)
    setContentView(mBinding.root)

    mScannerView = ZXingScannerView(this)
    mScannerView.setBorderColor(Color.WHITE)
    mScannerView.setIsBorderCornerRounded(true)
    mScannerView.setBorderCornerRadius(10)
    mScannerView.setLaserColor(Color.WHITE)
    mScannerView.setSquareViewFinder(true)
    mBinding.contentFrame.addView(mScannerView)

    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.title = getString(R.string.scan_to_pay)
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
    Log.v(
      TAG, rawResult.barcodeFormat.toString()
    ) // Prints the scan format (qrcode, pdf417 etc.)
    Toast.makeText(this, rawResult.text, Toast.LENGTH_SHORT).show()

    getHtml(rawResult.text)

    /*val layoutInflater = LayoutInflater.from(this)
    val view = layoutInflater.inflate(R.layout.dialog_success, null)
    val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
    titleTextView.text = getString(R.string.payment_successful)
    val descriptionTextView = view.findViewById<TextView>(R.id.descriptionTextView)
    descriptionTextView.text = getString(R.string.your_payment_has_been_successful)
    descriptionTextView.text = rawResult.text
    val okTextView = view.findViewById<TextView>(R.id.okTextView)
    okTextView.setOnClickListener {
      alertDialog.dismiss()
      finish()
    }
    val builder = AlertDialog.Builder(this)
    builder.setView(view)
    alertDialog = builder.create()
    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.setCancelable(false)
    alertDialog.show()*/
  }

  private fun getHtml(qrData: String) {
    val qrCode = QrCode(qrData)
    ApiClient().getApiClient("http://qr.shurjopay.com.bd/")?.create(ApiInterface::class.java)
      ?.pay(qrCode)?.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
          Log.d(TAG, "onResponse: ${response.body()}")
          val intent = Intent(this@QRScannerActivity, PaymentActivity::class.java)
          intent.putExtra("html", response.body())
          startActivity(intent)
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
          Log.e(TAG, "onFailure: ${t.message}", t)
        }
      })
  }

  override fun finish() {
    super.finish()
    overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> {
        finish()
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  companion object {
    private const val TAG = "QRScannerActivity"
    private const val CODE_SELECT_PHOTO = 111
  }

  fun toggleFlash(view: View) {
    mScannerView.toggleFlash()
  }

  fun pickImage(view: View) {
    //ImagePicker.create(this).showCamera(false).start()

    val photoPickerIntent = Intent(Intent.ACTION_PICK)
    photoPickerIntent.type = "image/*"
    startActivityForResult(photoPickerIntent, CODE_SELECT_PHOTO)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    /*// Get a list of picked images
    var images: List<Image> = ImagePicker.getImages(data)
    // or get a single image only
    var image: Image = ImagePicker.getFirstImageOrNull(data)*/

    if (requestCode == CODE_SELECT_PHOTO) {
      if (resultCode == RESULT_OK) {
        if (data != null) {
          // Get the URI of the selected file
          val uri: Uri? = data.data
          /*if (android.os.Build.VERSION.SDK_INT >= 29) {
            val bitmap: Bitmap = ImageDecoder.createSource(contentResolver, uri)
            decodeQR(bitmap)
          } else {
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            decodeQR(bitmap)
          }*/

          var bitmap: Bitmap? = null
          if (Build.VERSION.SDK_INT >= 29) {
            val source =
              ImageDecoder.createSource(
                applicationContext.contentResolver,
                uri!!
              )
            try {
              bitmap = ImageDecoder.decodeBitmap(source)
            } catch (e: IOException) {
              e.printStackTrace()
            }
          } else {
            try {
              bitmap = MediaStore.Images.Media.getBitmap(
                applicationContext.contentResolver,
                uri!!
              )
            } catch (e: IOException) {
              e.printStackTrace()
            }
          }

          decodeQR(bitmap)
        }
      }
    }

    super.onActivityResult(requestCode, resultCode, data)
  }

  private fun decodeQR(bitmap: Bitmap?) {
    var contents: String

    if (bitmap != null) {
      try {
        var intArray = IntArray(bitmap.width * bitmap.height)
        //copy pixel data from the Bitmap into the 'intArray' array
        bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        val source: LuminanceSource = RGBLuminanceSource(bitmap.width, bitmap.height, intArray)
        val newBitmap = BinaryBitmap(HybridBinarizer(source))

        val reader = MultiFormatReader()
        val result = reader.decode(newBitmap)
        contents = result.text
        Log.d(TAG, "decodeQR: contents = $contents")
        Toast.makeText(this, contents, Toast.LENGTH_LONG).show()
      } catch (e: Exception) {
        Log.e(TAG, "decodeQR: " + e.message, e)
        Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show()
      }
    } else {
      Log.e(TAG, "decodeQR: Can't decode picture!")
      Toast.makeText(this, "Can't decode picture!", Toast.LENGTH_SHORT).show()
    }
  }
}


