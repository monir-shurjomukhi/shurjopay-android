package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.ChangePassword
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ChangePasswordViewModel(application: Application) : BaseViewModel(application) {
  private val _changePassword = MutableLiveData<ChangePassword>()
  val changePassword: LiveData<ChangePassword>
    get() = _changePassword

  fun changePassword(changePassword: ChangePassword) {
    viewModelScope.launch {
      progress.value = true

      val response = try {
        apiClient.changePassword(changePassword)
      } catch (e: IOException) {
        Log.e(TAG, "changePassword: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "changePassword: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _changePassword.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "ChangePasswordViewModel"
  }
}
