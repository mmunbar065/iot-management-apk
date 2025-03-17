package com.example.tfg_iot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tfg_iot.data.repository.DeviceRepository
import com.example.tfg_iot.presentation.DeviceListScreen
import com.example.tfg_iot.presentation.DeviceViewModel
import com.example.tfg_iot.presentation.AddDeviceScreen
import com.example.tfg_iot.theme.TFGiotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización manual del ViewModel
        val viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DeviceViewModel(DeviceRepository()) as T
            }
        }).get(DeviceViewModel::class.java)

        setContent {
            // Configuración de la navegación
            val navController = rememberNavController()
            TFGiotTheme {
                // Usar NavHost para gestionar las pantallas
                NavHost(navController = navController, startDestination = "deviceListScreen") {
                    composable("deviceListScreen") {
                        DeviceListScreen(navController = navController, viewModel = viewModel)
                    }
                    composable("addDeviceScreen") {
                        AddDeviceScreen(navController = navController, viewModel = viewModel)
                    }
                }
            }
        }
    }
}
