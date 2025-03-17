package com.example.tfg_iot.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tfg_iot.data.local.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeviceListScreen(
    navController: NavController,
    viewModel: DeviceViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val devices by viewModel.devices.collectAsState()


    // Llama a la API al cargar la pantalla
    LaunchedEffect(Unit) { viewModel.fetchDevices() }

    Scaffold(
        // Configuración de la barra de navegación
        topBar = {
            TopAppBar(
                title = { Text("Lista de Dispositivos") },
                actions = {
                    IconButton(onClick = { navController.navigate("addDeviceScreen") }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar Dispositivo")
                    }
                    // Botón de refrescar
                    IconButton(onClick = {
                        // Llamar a la función de refrescar, por ejemplo, para actualizar la lista de dispositivos
                        viewModel.fetchDevices()  // Asumiendo que tienes esta función en tu ViewModel

                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                },

            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (devices.isEmpty()) {
                Text(
                    text = "No hay dispositivos disponibles",
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = { viewModel.fetchDevices() },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Refrescar")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(devices) { device ->
                        DeviceItem(
                            device = device,
                            onDelete = { viewModel.deleteDevice(it) },
                            onToggleStatus = { id, status ->
                                viewModel.toggleDevice(id, status)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceItem(
    device: Device,
    onDelete: (Int) -> Unit,
    onToggleStatus: (Int, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${device.name}")
            Text(text = "ID: ${device.id} - ${device.type}")
            Text(text = "Estado: ${device.status}")
            Text(text = "Batería: ${device.batteryLevel ?: "N/A"}%")
            Text(text = "Temperatura: ${device.temperature ?: "N/A"}°C")
            Text(text = "Humedad: ${device.humidity ?: "N/A"}%")
            Text(text = "Presión: ${device.pressure ?: "N/A"} hPa")
            Text(text = "Último movimiento: ${device.lastMotion ?: "N/A"}")
            Text(text = "Última actualización: ${device.lastUpdate}")

            Spacer(modifier = Modifier.height(8.dp))

            // Botones para cambiar el estado y eliminar el dispositivo
            Row {
                // Botón para cambiar el estado
                Button(
                    onClick = {
                        val newStatus = if (device.status == "on") "off" else "on"
                        onToggleStatus(device.id, newStatus)
                    }
                ) {
                    Text("Cambiar Estado")
                }
                Spacer(modifier = Modifier.width(8.dp))

                // Botón para eliminar el dispositivo
                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Eliminar")
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmar eliminación") },
                    text = { Text("¿Realmente desea borrar el dispositivo?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                onDelete(device.id)
                                showDialog = false
                            }
                        ) {
                            Text("Sí, eliminar")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialog = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}
