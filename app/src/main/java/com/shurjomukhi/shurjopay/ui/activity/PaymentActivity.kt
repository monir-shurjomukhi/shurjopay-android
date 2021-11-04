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
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.databinding.ActivityPaymentBinding
import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.networking.ApiClient
import com.shurjomukhi.shurjopay.networking.ApiInterface
import com.shurjomukhi.shurjopay.ui.viewmodel.PaymentViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : AppCompatActivity() {

  private lateinit var binding: ActivityPaymentBinding
  private lateinit var viewModel: PaymentViewModel
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

    viewModel = ViewModelProvider(this).get(PaymentViewModel::class.java)
    progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)

    viewModel.progress.observe(this, {
      if (it) {
        showProgress()
      } else {
        hideProgress()
      }
    })
    viewModel.html.observe(this, {
      showWebsite(it)
    })

    val qrCode = intent.getStringExtra("qrCode")
    viewModel.getHtml(qrCode.toString())
  }

  private fun showWebsite(html: String) {
    binding.webView.settings.javaScriptEnabled = true
    binding.webView.settings.domStorageEnabled = true
    binding.webView.settings.loadsImagesAutomatically = true
    binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
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
