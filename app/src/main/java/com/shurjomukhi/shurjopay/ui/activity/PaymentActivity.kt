package com.shurjomukhi.shurjopay.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebView
import android.widget.ProgressBar
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import com.shurjomukhi.shurjopay.R
import android.webkit.WebViewClient
import com.shurjomukhi.shurjopay.ui.activity.PaymentActivity
import android.webkit.SslErrorHandler
import android.net.http.SslError
import android.util.Log
import android.view.MenuItem
import android.webkit.WebChromeClient
import com.shurjomukhi.shurjopay.databinding.ActivityPaymentBinding
import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.networking.ApiClient
import com.shurjomukhi.shurjopay.networking.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

  private lateinit var binding: ActivityPaymentBinding
  private lateinit var progressDialog: ProgressDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityPaymentBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // add back arrow to toolbar
    if (supportActionBar != null) {
      supportActionBar!!.setDisplayHomeAsUpEnabled(true)
      supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)
    val qrCode = intent.getStringExtra("qrCode")
    getHtml(qrCode.toString())
  }

  private fun getHtml(qrData: String) {
    progressDialog.show()
    val qrCode = QrCode(qrData)
    ApiClient().getApiClient("http://qr.shurjopay.com.bd/")?.create(ApiInterface::class.java)
      ?.pay(qrCode)?.enqueue(object : Callback<String> {
        override fun onResponse(call: Call<String>, response: Response<String>) {
          Log.d(TAG, "onResponse: ${response.body()}")
          if (progressDialog.isShowing) {
            progressDialog.dismiss()
          }
          showWebsite(response.body().toString())
        }

        override fun onFailure(call: Call<String>, t: Throwable) {
          Log.e(TAG, "onFailure: ${t.message}", t)
          if (progressDialog.isShowing) {
            progressDialog.dismiss()
          }
        }
      })
  }

  private fun showWebsite(html: String) {
    binding.webView.settings.javaScriptEnabled = true
    binding.webView.settings.domStorageEnabled = true
    binding.webView.settings.loadsImagesAutomatically = true
    binding.webView.loadDataWithBaseURL(null, html!!, "text/html", "UTF-8", null)
    binding.webView.webViewClient = object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        Log.d(TAG, "shouldOverrideUrlLoading: url = $url")
        return false
      }

      override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
        handler.proceed()
      }
    }
    binding.webView.webChromeClient = object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        binding.progressBar.progress = newProgress
      }
    }
  }

  private fun showProgress() {
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)
    progressDialog.show()
  }

  private fun hideProgress() {
    if (progressDialog.isShowing) {
      progressDialog.dismiss()
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // handle arrow click here
    if (item.itemId == android.R.id.home) {
      onBackPressed()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onBackPressed() {
    if (binding.webView.canGoBack()) {
      binding.webView.goBack()
    } else {
      super.onBackPressed()
    }
  }

  companion object {
    private const val TAG = "PaymentActivity"
  }
}