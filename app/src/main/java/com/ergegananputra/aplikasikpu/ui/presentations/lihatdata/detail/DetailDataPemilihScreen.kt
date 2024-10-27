package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.detail

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu._dev.Mock.Companion.appContainer
import com.ergegananputra.aplikasikpu.ui.navigations.DataPemilihActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.components.ConfirmationAlertDialog
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import com.ergegananputra.aplikasikpu.utils.toSimpleReadableString

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DetailDataPemilihScreenDeveloperPreview() {
    val viewModel = DetailDataPemilihViewModel(
        appContainer = appContainer,
        id = 0
    )
    AplikasiKPUTheme {
        DetailDataPemilihScreen(viewModel = viewModel, mainEvent = {})
    }
}


@Composable
fun DetailDataPemilihScreen(
    viewModel: DetailDataPemilihViewModel,
    modifier: Modifier = Modifier,
    mainEvent: (DataPemilihActivityEvent) -> Unit
) {
    val localContext = androidx.compose.ui.platform.LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isDone) {
        if (state.isDone) {
            mainEvent(DataPemilihActivityEvent.OnBack)
        }
    }

    if (state.isDeleteConfirmationOpen) {
        ConfirmationAlertDialog(
            imagePainter = Icons.Filled.Delete,
            title = "Hapus Data",
            text = "Apakah Anda yakin ingin menghapus data ini?",
            onDismissRequest = { viewModel.hideDeleteConfirmation() },
            onConfirmation = { viewModel.deleteData(state.id) }
        )
    }

    state.errorMessage?.let {
        Toast.makeText(
            localContext,
            it,
            Toast.LENGTH_SHORT
        ).show()

        viewModel.clearErrorMessage()
    }


    val scrollY = rememberScrollState()

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
                .verticalScroll(scrollY)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_access_time_24),
                        contentDescription = "Access Time",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(16.dp)
                    )
                    Text(
                        text = state.data?.tanggalPendataan?.toSimpleReadableString() ?: "Tidak ada tanggal",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = state.data?.namaLengkap ?: "Tidak ada nama lengkap",
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_badge_24),
                        contentDescription = "NIK",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = state.data?.nik ?: "Tidak ada nik",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_phone_24),
                        contentDescription = "Handphone",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = state.data?.nomorHandphone ?: "-",
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_outline_person_24),
                        contentDescription = "Jenis Kelamin",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(end = 8.dp)
                    )
                    Text(
                        text = state.gender,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "Alamat",
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = state.data?.alamat ?: "Tidak ada alamat",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp
                    )
            )

            Text(
                text = "Bukti Pendataan",
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            AsyncImage(
                model = "https://masmoendigital.store/storage/${state.data?.imageUrl}",
                contentDescription = "Bukti Pendataan ${state.data?.id}",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp
                    )
                    .clip(RoundedCornerShape(24.dp))
            )


        }

    }
}