package com.shurjomukhi.shurjopay.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shurjomukhi.shurjopay.R
import com.shurjomukhi.shurjopay.model.Logout
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ProfileViewModel(application: Application) : BaseViewModel(application) {

  private val _logout = MutableLiveData<Logout>()
  val logout: LiveData<Logout>
    get() = _logout

  fun logout() {
    viewModelScope.launch {
      progress.value = true
      val logout = Logout(preference.getMobileNumber().toString(), null)

      val response = try {
        apiClient.logout(logout)
      } catch (e: IOException) {
        Log.e(TAG, "logout: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      } catch (e: HttpException) {
        Log.e(TAG, "logout: ${e.message}", e)
        progress.value = false
        message.value = R.string.unable_to_connect
        return@launch
      }

      progress.value = false
      if (response.isSuccessful && response.body() != null) {
        _logout.value = response.body()
      } else {
        message.value = R.string.unable_to_connect
      }
    }
  }

  companion object {
    private const val TAG = "ProfileViewModel"
  }
}
