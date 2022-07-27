package com.cci.todolist.tasks

import com.cci.todolist.utils.TodoListMoshi.moshi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_PATH = "http://www.boredapi.com/api/"

private val retrofit = Retrofit.Builder()
  .addConverterFactory(MoshiConverterFactory.create(moshi))
  .baseUrl(BASE_PATH)
  .build()

interface BoredApiService {
  @GET("activity")
  suspend fun getActivity(): BoredActivity
}

object BoredApi {
  val retrofitService: BoredApiService by lazy {
    retrofit.create(BoredApiService::class.java)
  }
}
