package com.ergegananputra.aplikasikpu.ui.activities

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.KPUApplication
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.formentry.FormEntryScreen
import com.ergegananputra.aplikasikpu.ui.presentations.formentry.FormEntryViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import java.io.File

class FormEntryActivity : ComponentActivity() {

    private lateinit var formEntryViewModel: FormEntryViewModel

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val cameraPermission = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.CAMERA,
                )
            )
            LaunchedEffect(Unit) {
                cameraPermission.launchMultiplePermissionRequest()
            }

            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            formEntryViewModel = viewModel {
                FormEntryViewModel(appContainer = (application as KPUApplication).appContainer)
            }
            AplikasiKPUTheme {
                Scaffold(
                    topBar = {
                        MediumTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                                titleContentColor = MaterialTheme.colorScheme.onSurface,
                            ),
                            title = {
                                Text(
                                    "Form Entry",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { onEvent(FormEntryActivityEvent.OnBack) }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Button to go back"
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    FormEntryScreen(
                        viewModel = formEntryViewModel,
                        mainEvent = ::onEvent,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }

    private fun onEvent(action: FormEntryActivityEvent) {
        when (action) {
            is FormEntryActivityEvent.OnBack -> {
                setResult(RESULT_OK)
                finish()
            }

            FormEntryActivityEvent.GoToMaps -> {
                val intentToMaps = Intent(this, MapsActivity::class.java)
                launcherToMaps.launch(intentToMaps)
            }

            is FormEntryActivityEvent.LaunchCamera -> {
                val available = checkCameraHardware(this@FormEntryActivity)
                if (available) {
                    launchCamera()
                }

            }

            is FormEntryActivityEvent.UploadPhoto -> {
                launchImagePicker()
            }

            else -> {}
        }
    }

    private fun launchImagePicker() {
        launcherPickImage.launch("image/*")
    }

    private var tempPhotoUri : Uri? = null

    private fun launchCamera() {
        try {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            val photoFile = File.createTempFile(
                "JPEG_${System.currentTimeMillis()}_",
                ".jpg",
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            )
            val photoUri = FileProvider.getUriForFile(
                this,
                "com.ergegananputra.aplikasikpu.provider",
                photoFile
            )

            launcherTakePicture.launch(photoUri)
            tempPhotoUri = photoUri

        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.e("FormEntryActivity", "Error: ${e.message}")
            formEntryViewModel.displayErrorMessage("Kamera tidak ditemukan")
        }
    }

    private val launcherToMaps = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val address = result.data?.getStringExtra(FormEntryActivityEvent.ADDRESS)
            formEntryViewModel.updateAddress(address)
        }
    }

    private val launcherTakePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Handle the captured photo
            tempPhotoUri?.let {
                formEntryViewModel.updateCapturedPhoto(it)
            }

        }
        tempPhotoUri = null
    }

    private val launcherPickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            formEntryViewModel.updateCapturedPhoto(it)
        }
    }


    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
    }
}