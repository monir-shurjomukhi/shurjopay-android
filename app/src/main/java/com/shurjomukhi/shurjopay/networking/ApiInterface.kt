package com.shurjomukhi.shurjopay.networking

import com.shurjomukhi.shurjopay.model.Login
import com.shurjomukhi.shurjopay.model.Otp
import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.model.Registration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

  ///////////////////// GET ///////////////////

  /*@GET("insertcheck/{mobile_number}")
  fun insertCheck(
    @Path("mobile_number") mobileNumber: String
  ): Call<InsertCheck>

  @GET("counterNumbers/createCounterNumber/")
  fun createCounterNumber(
    @Header("Authorization") token: String
  ): Call<CounterNumber>

  @GET("customers/getByMobileNumber/{mobile_number}")
  fun getCustomer(
    @Header("Authorization") token: String,
    @Path("mobile_number") mobileNumber: String
  ): Call<Customer>*/

  //////////////////// POST ///////////////////

  @POST("info")
  suspend fun getHtml(
    @Body qrCode: QrCode
  ): Response<String>

  @POST("customer-register")
  suspend fun register(
    @Body registration: Registration
  ): Response<Registration>

  @POST("verify-otp")
  suspend fun verifyOTP(
    @Body otp: Otp
  ): Response<Otp>

  @POST("customer-login")
  suspend fun login(
    @Body login: Login
  ): Response<Login>
}
