package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.Registration
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RegistrationViewModel(application: Application) : BaseViewModel(application) {
  private val _registration = MutableLiveData<Registration>()
  val registration: LiveData<Registration>
    get() = _registration

  fun register(registration: Registration) {
    viewModelScope.launch {
      progress.value = true

      val response = try {
        apiClient.register(registration)
      } catch (e: IOException) {
        Log.e(TAG, "register: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "register: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _registration.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "RegistrationViewModel"
  }
}