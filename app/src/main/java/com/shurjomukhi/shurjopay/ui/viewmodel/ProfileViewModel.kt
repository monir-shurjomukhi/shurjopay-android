package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shurjomukhi.shurjopay.model.Logout

class ProfileViewModel(application: Application) : BaseViewModel(application) {

  private val _logout = MutableLiveData<Logout>()
  val logout: LiveData<Logout>
    get() = _logout
}
