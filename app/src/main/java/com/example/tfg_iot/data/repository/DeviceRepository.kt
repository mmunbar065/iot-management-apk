package com.example.tfg_iot.data.repository

import com.example.tfg_iot.data.local.Device
import com.example.tfg_iot.data.local.DevicesResponse
import com.example.tfg_iot.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeviceRepository {
    private val apiService = ApiClient.apiService

    fun fetchDevices(callback: (DevicesResponse?) -> Unit) {
        apiService.getDevices().enqueue(object : Callback<DevicesResponse> {
            override fun onResponse(
                call: Call<DevicesResponse>,
                response: Response<DevicesResponse>
            ) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DevicesResponse>, t: Throwable) {
                callback(null)
            }
        })
    }

    suspend fun deleteDevice(deviceId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.deleteDevice(deviceId)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    data class StatusRequest(val status: String)

    data class ApiResponse(
        val message: String? = null,
        val error: String? = null
    )

    suspend fun toggleDevice(deviceId: Int, status: String): Boolean {
        val statusRequest = StatusRequest(status)

        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.toggleDevice(deviceId, statusRequest)
                response.isSuccessful
            } catch (e: Exception) {
                false // Si hay error, devuelve falso
            }
        }
    }

    //Función para añadir un dispositivo
    suspend fun addDevice(device: Device): Response<Void> {

        return try {
            apiService.addDevice(device)

        } catch (e: Exception) {
            Response.error(500, ResponseBody.create(null, "Error de red"))
        }
    }
}
