package com.example.fullonnettestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.fullonnettestapp.data.repository.UsersRetrofitRepository
import com.example.fullonnettestapp.screens.UsersScreen
import com.example.fullonnettestapp.ui.theme.FullOnNetTestAppTheme
import com.example.fullonnettestapp.viewmodel.UsersViewModel
import com.example.fullonnettestapp.viewmodel.UsersViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {

    private val repository by lazy { UsersRetrofitRepository() }
    private val usersViewModel: UsersViewModel by viewModels {
        UsersViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FullOnNetTestAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RequestInternetPermission(usersViewModel = usersViewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestInternetPermission(usersViewModel: UsersViewModel, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(android.Manifest.permission.INTERNET)

    if (permissionState.status.isGranted) {
        UsersScreen(usersViewModel = usersViewModel, modifier = modifier)
    } else {
        Column {
            val textToShow = if (permissionState.status.shouldShowRationale) {
                "Internet is needed for this app. Please grant the permission."
            } else {
                "Internet permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}
