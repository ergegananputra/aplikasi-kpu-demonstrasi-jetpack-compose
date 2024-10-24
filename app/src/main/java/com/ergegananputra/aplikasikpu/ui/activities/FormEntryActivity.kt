package com.ergegananputra.aplikasikpu.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.formentry.FormEntryScreen
import com.ergegananputra.aplikasikpu.ui.presentations.formentry.FormEntryViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

class FormEntryActivity : ComponentActivity() {

    private lateinit var formEntryViewModel: FormEntryViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            formEntryViewModel = viewModel(FormEntryViewModel::class.java)
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
        }
    }

    private val launcherToMaps = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Log.d("FormEntryActivity", "Success")
        }
    }
}