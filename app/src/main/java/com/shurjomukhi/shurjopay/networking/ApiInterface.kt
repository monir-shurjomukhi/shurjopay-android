package com.shurjomukhi.shurjopay.networking

import com.shurjomukhi.shurjopay.model.QrCode
import com.shurjomukhi.shurjopay.model.Registration
import retrofit2.Call
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
  fun getHtml(
    @Body qrCode: QrCode
  ): Call<String>

  @POST("customer-register")
  fun register(
    @Body registration: Registration
  ): Call<Registration>

  /*@POST("customers")
  fun signUp(
    @Body signUp: SignUpData
  ): Call<SignUp>

  @POST("verify_input_code")
  fun verifyOtp(
    @Body otpData: OtpData
  ): Call<String>

  @POST("update_customer_logins")
  fun updateLogin(
    @Body updateLogin: UpdateLogin
  ): Call<UpdateLogin>

  @POST("auth/login")
  fun login(
    @Body login: Login
  ): Call<String>

  @POST("agentRegistrationFromWeb")
  fun registerAgent(
    @Body agentRegistrationData: AgentRegistrationData
  ): Call<AgentRegistration>

  @POST("breedOfAnimals/typeOfBreeds")
  fun getAnimalBreed(
    @Body breed: Breed
  ): Call<Breed>*/
}
