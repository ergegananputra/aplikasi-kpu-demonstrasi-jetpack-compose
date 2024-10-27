package com.ergegananputra.aplikasikpu.ui.presentations.dashboard

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu.ui.navigations.MainActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.components.KpuButton
import com.ergegananputra.aplikasikpu.ui.presentations.components.LogOutAlertDialog
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DashboardScreenDeveloperPreview() {
    val viewModel = DashboardViewModel()
    AplikasiKPUTheme {
        DashboardScreen(viewModel)
    }
}


@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    modifier: Modifier = Modifier,
    mainEvent: (MainActivityEvent) -> Unit = {},
) {
    val localContext = LocalContext.current
    val state by viewModel.state.collectAsState()

    if (state.isDialogOpen) {
        LogOutAlertDialog(
            onDismissRequest = viewModel::onDismissDialog,
            onConfirmation = {
                mainEvent(MainActivityEvent.LogOut)
            }
        )
    }

    LaunchedEffect(state.errorMessage) {
        state.errorMessage?.let { errorMessage ->
            Toast.makeText(
                localContext,
                errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearErrorMessage()
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo_kpu_sample),
                            contentDescription = "Logo KPU",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(32.dp)
                                .weight(1f)
                        )

                        Text(
                            text = "KPU",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        KpuButton(
                            text = "Informasi",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                mainEvent(MainActivityEvent.GoToInformasi)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_library_books_24),
                                contentDescription = "Tombol Informasi",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }

                        KpuButton(
                            text = "Form Entry",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                mainEvent(MainActivityEvent.GoToFormEntry)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_edit_document_24),
                                contentDescription = "Tombol Form Entry",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }

                        KpuButton(
                            text = "Lihat Data",
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                mainEvent(MainActivityEvent.GoToLihatData)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_baseline_document_scanner_24),
                                contentDescription = "Tombol Lihat Data",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.3f)
                            )

                            KpuButton(
                                text = "Keluar",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.fillMaxWidth(),
                                onClick = viewModel::onButtonLogoutClick
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_baseline_exit_to_app_24),
                                    contentDescription = "Tombol Keluar",
                                    tint = MaterialTheme.colorScheme.onError,
                                    modifier = Modifier.padding(end = 16.dp)
                                )
                            }

                            Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.7f)
                            )
                        }


                    }
                }

            }

            Text(
                text = "Versi ${state.appVersion}",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            )

        }
    }
}


