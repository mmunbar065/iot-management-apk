package com.example.tfg_iot.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tfg_iot.data.local.Device
import com.example.tfg_iot.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DeviceViewModel(private val repository: DeviceRepository) : ViewModel() {

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices

    fun fetchDevices() {
        viewModelScope.launch {
            repository.fetchDevices { response ->
                response?.let {
                    _devices.value = it.devices.toList() // Actualizamos la lista
                } ?: run {
                    _devices.value = emptyList() // Si hay un error, vaciamos la lista
                }
            }
        }
    }

    /**
     * Función para eliminar un dispositivo
     */
    fun deleteDevice(deviceId: Int) {
        viewModelScope.launch {
            val success = repository.deleteDevice(deviceId)
            if (success) {
                _devices.value = _devices.value.filter { it.id != deviceId }
            }
        }
    }

    /**
     * Función para cambiar el estado de un dispositivo
     */
    fun toggleDevice(deviceId: Int, status: String) {
        viewModelScope.launch {
            //val newStatus = if (currentStatus == "on") "off" else "on"
            val success = repository.toggleDevice(deviceId, status)
            if (success) {
                fetchDevices() // Recargar la lista después de cambiar el estado
            }
        }
    }


    /**
     * Función para añadir un dispositivo
     *
     *
     */
    fun addDevice(device: Device, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            val success = repository.addDevice(device)
            if (success.isSuccessful) {
                onSuccess()
            } else {
                // Manejar el error de añadir dispositivo
            onError("Error al añadir el dispositivo")
            }

        }
    }

}
