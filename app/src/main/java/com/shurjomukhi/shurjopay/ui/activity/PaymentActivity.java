package com.shurjomukhi.shurjopay.ui.activity;

import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


import com.shurjomukhi.shurjopay.R;
import com.shurjomukhi.shurjopay.networking.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentActivity extends AppCompatActivity {
  private static final String TAG = "PaymentActivity";
  private WebView webView;
  private ProgressBar progressBar;
  private ProgressDialog progressDialog;
  private String sdkType;
  private String html;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_payment);

    // add back arrow to toolbar
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    webView = findViewById(R.id.webView);
    progressBar = findViewById(R.id.progressBar);
    progressDialog = new ProgressDialog(this);
    //requiredDataModel = (RequiredDataModel) getIntent().getSerializableExtra(SPayConstants.DATA);
    //Log.d(TAG, "onCreate: requiredDataModel = " + requiredDataModel);
    html = getIntent().getStringExtra("html");

    showWebsite(html);
  }

  private void showWebsite(String html) {
    final String[] previousUrl = {""};
    final String[] currentUrl = {""};
    webView.getSettings().setJavaScriptEnabled(true);
    webView.getSettings().setDomStorageEnabled(true);
    webView.getSettings().setLoadsImagesAutomatically(true);
    webView.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.d(TAG, "shouldOverrideUrlLoading: url = " + url);

        /*previousUrl[0] = currentUrl[0];
        currentUrl[0] = url;

        Log.d(TAG, "shouldOverrideUrlLoading: previousUrl[0] = " + previousUrl[0]);
        Log.d(TAG, "shouldOverrideUrlLoading: currentUrl[0] = " + currentUrl[0]);

        if (currentUrl[0].contains("return_url.php")) {
          ShurjoPaySDK.listener.onFailed(SPayConstants.Exception.PAYMENT_CANCELLED_BY_USER);
          finish();
          if (previousUrl[0].contains("cancel=ok")) {
            ShurjoPaySDK.listener.onFailed(SPayConstants.Exception.PAYMENT_CANCELLED);
            finish();
          } else {
            getTransactionInfo();
          }
        } else {
          view.loadUrl(url);
        }*/
        return false;
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
      }
    });
    webView.setWebChromeClient(new WebChromeClient() {
      @Override
      public void onProgressChanged(WebView view, int newProgress) {
        progressBar.setProgress(newProgress);
      }
    });
  }

  private void showProgress(String message) {
    progressDialog.setMessage(message);
    progressDialog.setCancelable(false);
    progressDialog.show();
  }

  private void hideProgress() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.dismiss();
    }
  }

  /*@Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    // Check if the key event was the Back button and if there's history
    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
      webView.clearHistory();
      showWebsite(html);
      return true;
    }
    // If it wasn't the Back key or there's no web page history, bubble up to the default
    // system behavior (probably exit the activity)
    return super.onKeyDown(keyCode, event);
  }*/

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // handle arrow click here
    if (item.getItemId() == android.R.id.home) {
      //ShurjoPaySDK.listener.onFailed(SPayConstants.Exception.PAYMENT_CANCELLED);
      finish(); // close this activity and return to preview activity (if there is any)
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    if (webView.canGoBack()) {
      webView.goBack();
    } else {
      super.onBackPressed();
    }
  }
}
