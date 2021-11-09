package com.shurjomukhi.shurjopay.model

data class Registration(
  val name: String,
  val email: String?,
  val mobile_no: String,
  val password: String,
  val password_confirmation: String,
  val message: String?,
)
