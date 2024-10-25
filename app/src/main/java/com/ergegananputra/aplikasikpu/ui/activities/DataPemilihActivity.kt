package com.ergegananputra.aplikasikpu.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.KPUApplication
import com.ergegananputra.aplikasikpu.ui.navigations.DataPemilihActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.DataPemilihScreen
import com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.DataPemilihViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

class DataPemilihActivity : ComponentActivity() {

    private lateinit var dataPesertaViewModel: DataPemilihViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
            dataPesertaViewModel = viewModel {
                DataPemilihViewModel(appContainer = (application as KPUApplication).appContainer)
            }

            LaunchedEffect(key1 = dataPesertaViewModel) {
                dataPesertaViewModel.fetchDataPeserta()
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
                                    "Data Pemilih",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { onEvent(DataPemilihActivityEvent.OnBack) }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Button to go back"
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) { innerPadding ->
                    DataPemilihScreen(
                        viewModel = dataPesertaViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun onEvent(event: DataPemilihActivityEvent) {
        when (event) {
            DataPemilihActivityEvent.OnBack -> {
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }
}
