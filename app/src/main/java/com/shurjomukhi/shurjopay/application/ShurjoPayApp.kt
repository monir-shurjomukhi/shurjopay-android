package com.shurjomukhi.shurjopay.application

import android.app.Application
import com.microblink.MicroblinkSDK

class ShurjoPayApp : Application() {
  override fun onCreate() {
    super.onCreate()
    MicroblinkSDK.setLicenseFile("MB_com.shurjomukhi.shurjopay_BlinkCard_Android_2020-07-30.mblic", this)
  }
}
