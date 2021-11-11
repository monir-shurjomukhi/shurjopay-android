package com.shurjomukhi.shurjopay.ui.activity

import android.app.ProgressDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

open class BaseActivity: AppCompatActivity() {
  private lateinit var progressDialog: ProgressDialog

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
  }

  protected fun showProgress() {
    progressDialog = ProgressDialog(this)
    progressDialog.setMessage("Please Wait...")
    progressDialog.setCancelable(false)
    progressDialog.show()
  }

  protected fun hideProgress() {
    if (progressDialog.isShowing) {
      progressDialog.dismiss()
    }
  }

  protected fun shortToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  protected fun shortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  protected fun longToast(message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  protected fun longToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
  }

  protected fun shortSnack(view: View, message: Int) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_SHORT).show()
  }

  protected fun longSnack(view: View, message: Int) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_LONG).show()
  }

  protected fun actionSnack(view: View, message: Int, action: Int, listener: View.OnClickListener) {
    Snackbar.make(view, getString(message), Snackbar.LENGTH_INDEFINITE).setAction(action, listener).show()
  }
}