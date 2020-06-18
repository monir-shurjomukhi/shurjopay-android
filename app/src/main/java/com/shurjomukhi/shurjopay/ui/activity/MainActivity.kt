package com.shurjomukhi.shurjopay.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shurjomukhi.shurjopay.R

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navView: BottomNavigationView = findViewById(R.id.nav_view)

    val navController = findNavController(R.id.nav_host_fragment)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(
      setOf(
        R.id.navigation_home,
        R.id.navigation_transactions,
        R.id.navigation_notifications,
        R.id.navigation_profile
      )
    )
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
  }

  fun openScanner(view: View) {
    if (hasCameraPermission()) {
      startActivity(Intent(this, ScannerActivity::class.java))
      overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
    } else {
      requestCameraPermission()
    }
  }

  private fun hasCameraPermission(): Boolean {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
      != PackageManager.PERMISSION_GRANTED
    ) {
      // Permission is not granted
      return false
    }
    return true
  }

  private fun requestCameraPermission() {
    ActivityCompat.requestPermissions(
      this,
      arrayOf(Manifest.permission.CAMERA),
      PERMISSION_REQUEST_CODE
    );
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when(requestCode) {
      PERMISSION_REQUEST_CODE -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
          startActivity(Intent(this, ScannerActivity::class.java))
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top)
      }
    }
  }

  companion object {
    private const val PERMISSION_REQUEST_CODE = 1
  }
}