package com.ergegananputra.aplikasikpu.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.KPUApplication
import com.ergegananputra.aplikasikpu.ui.navigations.DataPemilihActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail.DetailDataPemilihScreen
import com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail.DetailDataPemilihViewModel
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

class DetailDataPemilihActivity : ComponentActivity() {

    private lateinit var detailDataPemilihViewModel: DetailDataPemilihViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val detailId = intent.getIntExtra(DataPemilihActivityEvent.Keys.DETAIL_ID.name, -1)

        if (detailId == -1) {
            setResult(RESULT_CANCELED)
            finish()
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

            detailDataPemilihViewModel = viewModel {
                DetailDataPemilihViewModel(appContainer = (application as KPUApplication).appContainer, id = detailId).also {
                    it.fetchDetailData(detailId)
                }
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
                            scrollBehavior = scrollBehavior,
                            actions = {
                                IconButton(onClick = { onEvent(DataPemilihActivityEvent.ShowDeleteConfirmation) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Button to go back"
                                    )
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                ) { innerPadding ->

                    DetailDataPemilihScreen(
                        mainEvent = ::onEvent,
                        modifier = Modifier.padding(innerPadding),
                        viewModel = detailDataPemilihViewModel
                    )
                }
            }
        }
    }

    private fun onEvent(event: DataPemilihActivityEvent) {
        when (event) {
            is DataPemilihActivityEvent.OnBack -> {
                setResult(RESULT_OK)
                finish()
            }
            is DataPemilihActivityEvent.ShowDeleteConfirmation -> {
                detailDataPemilihViewModel.showDeleteConfirmation()
            }
            is DataPemilihActivityEvent.OnDelete -> {
                detailDataPemilihViewModel.deleteData(event.id)
            }
            else -> {
                // Do nothing
            }
        }
    }


}