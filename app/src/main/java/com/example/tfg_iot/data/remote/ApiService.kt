package com.example.tfg_iot.data.remote

import com.example.tfg_iot.data.local.Device
import com.example.tfg_iot.data.local.DevicesResponse
import com.example.tfg_iot.data.repository.DeviceRepository
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface ApiService {
    @GET("devices") // Asegúrate de que la ruta coincide con tu API
    fun getDevices(): Call<DevicesResponse>

    @GET("/devices/{type}")
    suspend fun getDevicesByType(@Path("type") type: String): Response<List<Device>>

    @POST("/add_device")
    suspend fun addDevice(@Body newDevice: Device): Response<Void>

    @DELETE("/delete_device/{id}")
    suspend fun deleteDevice(@Path("id") id: Int): Response<Void>

    @PUT("/update_device/{id}")
    suspend fun toggleDevice(
        @Path("id") id: Int,
        @Body statusRequest: DeviceRepository.StatusRequest): Response<DeviceRepository.ApiResponse>

    @GET("/get_device/{id}")
    suspend fun getDevice(@Path("id") id: Int): Response<Device>
}