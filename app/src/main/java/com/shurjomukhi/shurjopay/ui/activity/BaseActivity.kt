package com.shurjomukhi.shurjopay.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity() {
  private lateinit var progressDialog: ProgressDialog

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)

    progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)
  }

  protected fun showProgress() {
    progressDialog.show()
  }

  protected fun hideProgress() {
    if (progressDialog.isShowing) {
      progressDialog.dismiss()
    }
  }

  protected fun shortSnack(view: View, message: Int) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_SHORT).show()
  }

  protected fun longSnack(view: View, message: Int) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_LONG).show()
  }
}