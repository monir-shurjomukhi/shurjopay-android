package com.shurjomukhi.shurjopay.model

data class Otp(
  val verify_code: String,
  val mobile_no: String,
  val message: String?
)
