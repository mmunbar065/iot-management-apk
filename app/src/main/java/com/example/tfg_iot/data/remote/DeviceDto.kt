package com.example.tfg_iot.data.remote

import com.example.tfg_iot.data.local.Device

/**
 * Data Transfer Object (DTO) para representar un dispositivo en las respuestas de la API.
 */
data class DeviceDto(
    val id: Int,
    val name: String,
    val type: String,
    val status: String, // "on" o "off"
    val batteryLevel: Int?, // Puede ser nulo
    val temperature: Float?, // Puede ser nulo
    val humidity: Float?, // Puede ser nulo
    val pressure: Float?, // Puede ser nulo
    val lastMotion: String?, // Puede ser nulo (Formato: "YYYY-MM-DD HH:MM:SS")
    val timestamp: String, // Fecha de creación
    val lastUpdate: String // Última actualización
)

/**
 * Convierte un DeviceDTO en una entidad Device para Room.
 */
fun DeviceDto.toDeviceEntity(): Device {
    return Device(
        id = this.id,
        type = this.type,
        name = this.name,
        status = this.status,
        batteryLevel = this.batteryLevel,
        temperature = this.temperature,
        humidity = this.humidity,
        pressure = this.pressure,
        lastMotion = this.lastMotion,
        timestamp = this.timestamp,
        lastUpdate = this.lastUpdate
    )
}
