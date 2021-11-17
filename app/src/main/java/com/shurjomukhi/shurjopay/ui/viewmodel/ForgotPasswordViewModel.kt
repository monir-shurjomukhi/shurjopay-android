package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.ForgotPassword
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ForgotPasswordViewModel(application: Application) : BaseViewModel(application) {
  private val _forgotPassword = MutableLiveData<ForgotPassword>()
  val forgotPassword: LiveData<ForgotPassword>
    get() = _forgotPassword

  fun forgotPassword(forgotPassword: ForgotPassword) {
    viewModelScope.launch {
      progress.value = true

      val response = try {
        apiClient.forgotPassword(forgotPassword)
      } catch (e: IOException) {
        Log.e(TAG, "forgotPassword: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "forgotPassword: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _forgotPassword.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "ForgotPasswordViewModel"
  }
}
