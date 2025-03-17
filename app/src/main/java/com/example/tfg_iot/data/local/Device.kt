package com.example.tfg_iot.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DevicesResponse(
    @SerializedName("Devices") val devices: List<Device>
)

@Entity(tableName = "devices")
data class Device(
    @SerializedName("battery_level") val batteryLevel: Int? = null, // Por defecto null
    @SerializedName("humidity") val humidity: Float? = null, // Por defecto null
    @PrimaryKey @SerializedName("id") val id: Int, // Id obligatorio
    @SerializedName("last_motion") val lastMotion: String? = null, // Por defecto null
    @SerializedName("last_update") val lastUpdate: String = "", // Por defecto cadena vacía
    @SerializedName("name") val name: String, // Nombre obligatorio
    @SerializedName("pressure") val pressure: Float? = null, // Por defecto null
    @SerializedName("status") val status: String = "OFF", // Valor predeterminado "OFF"
    @SerializedName("temperature") val temperature: Float? = null, // Por defecto null
    @SerializedName("timestamp") val timestamp: String = "", // Por defecto cadena vacía
    @SerializedName("type") val type: String = "Otro" // Valor predeterminado "Otro"
)