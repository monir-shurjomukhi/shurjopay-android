package com.shurjomukhi.shurjopay.preference

import android.content.Context
import android.content.SharedPreferences
import com.shurjomukhi.shurjopay.utils.MOBILE_NUMBER
import com.shurjomukhi.shurjopay.utils.PREFERENCE_TITLE


class SPPreference(context: Context) {
  private val preferences: SharedPreferences =
    context.getSharedPreferences(PREFERENCE_TITLE, Context.MODE_PRIVATE)
  private val editor: SharedPreferences.Editor = preferences.edit()

  fun putMobileNumber(mobileNumber: String?) {
    editor.putString(MOBILE_NUMBER, mobileNumber)
    editor.apply()
  }

  fun getMobileNumber(): String? {
    return preferences.getString(MOBILE_NUMBER, "")
  }
}
