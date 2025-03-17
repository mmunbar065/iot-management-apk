package com.example.tfg_iot.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tfg_iot.data.local.Device



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDeviceScreen(
    navController: NavController,
    viewModel: DeviceViewModel = viewModel(),
    onDeviceAdded: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    val possibleDeviceTypes = listOf("Sensor temperatura", "Sensor humedad", "Actuador de luz", "Estación meteorológica", "Otro")
    var selectedDeviceType by remember { mutableStateOf(possibleDeviceTypes.first()) }

    //Estado del dropdown
    var expanded by remember { mutableStateOf(false) }

    var status by remember { mutableStateOf("ON") }

    var errorMessage by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    // Función para validar el formulario
    fun validateForm(): Boolean {
        if (name.isEmpty()) {
            errorMessage = "El nombre del dispositivo es obligatorio"
            return false
        }
        errorMessage = ""
        return true
    }
    // Función para manejar el envío del formulario
    fun onSubmit() {
        if (validateForm()) {
            isSubmitting = true
            val newDevice = Device(
                id = 0, // Si la API lo genera automáticamente, no necesitas enviarlo
                name = name,
                type = selectedDeviceType,
                status = status,
                batteryLevel = 100 // Se asume que el nuevo dispositivo inicia con batería llena
            )

            viewModel.addDevice(
                device = newDevice,
                onSuccess = {
                    isSubmitting = false
                    onDeviceAdded()
                    navController.popBackStack() // Volver a la lista de dispositivos
                },
                onError = { message ->
                    isSubmitting = false
                    errorMessage = message
                }
            )
        }
    }
    // Contenido de la pantalla
    Scaffold(
        // Configuración de la barra de navegación
        topBar = {
            TopAppBar(
                // Configuración del título y botones
                // Configuración del título
                title = { Text("Añadir Dispositivo") },
                // Configuración de los botones
                navigationIcon = {
                    // Botón de volver
                    IconButton(onClick = { navController.popBackStack() }) {
                        // Ícono de flecha de volver
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del dispositivo") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de tipo de dispositivo
            Box {
                OutlinedTextField(
                    value = selectedDeviceType,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de dispositivo") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Abrir desplegable")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                // Menú desplegable
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    possibleDeviceTypes.forEach { type ->
                        DropdownMenuItem(
                            text = { Text(text = type) },
                            onClick = {
                                selectedDeviceType = type
                                expanded = false // Cerrar el menú después de seleccionar
                            }
                        )
                    }
                }
            }

            // Selector de estado
            Row {
                Text("Estado:")
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { status = "ON" }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (status == "ON") Color.Green else Color.Gray
                )) {
                    Text("ON")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { status = "OFF" }, colors = ButtonDefaults.buttonColors(
                    containerColor = if (status == "OFF") Color.Red else Color.Gray
                )) {
                    Text("OFF")
                }
            }

            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }
            // Botones de acción
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }
                // Botón de guardar
                Button(
                    onClick = { onSubmit() },
                    enabled = !isSubmitting
                ) {
                    Text(if (isSubmitting) "Guardando..." else "Guardar")
                }
            }
        }
    }
}
