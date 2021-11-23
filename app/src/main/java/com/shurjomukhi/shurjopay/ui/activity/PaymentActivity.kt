package com.shurjomukhi.shurjopay.ui.activity

import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.databinding.ActivityPaymentBinding
import com.shurjomukhi.shurjopay.ui.viewmodel.PaymentViewModel

class PaymentActivity : BaseActivity() {

  private lateinit var binding: ActivityPaymentBinding
  private lateinit var viewModel: PaymentViewModel

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

    viewModel.progress.observe(this, {
      if (it) {
        showProgress()
      } else {
        hideProgress()
      }
    })
    viewModel.message.observe(this, {
      shortToast(it)
      if (it == R.string.unable_to_connect) onBackPressed()
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
    binding.webView.getSettings().saveFormData = true;
    binding.webView.getSettings().allowContentAccess = true;
    binding.webView.getSettings().allowFileAccess = true;
    binding.webView.getSettings().allowFileAccessFromFileURLs = true;
    binding.webView.getSettings().allowUniversalAccessFromFileURLs = true;
    binding.webView.isClickable = true
    if (html.startsWith("http")) {
      binding.webView.loadUrl(html)
    } else {
      binding.webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null)
    }
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
