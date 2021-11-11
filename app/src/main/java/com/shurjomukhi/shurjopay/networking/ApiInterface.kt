package com.shurjomukhi.shurjopay.networking

import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.model.Registration
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

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
}
