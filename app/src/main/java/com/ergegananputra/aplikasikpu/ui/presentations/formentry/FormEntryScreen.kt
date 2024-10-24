package com.ergegananputra.aplikasikpu.ui.presentations.formentry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.components.KpuButton
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun FormEntryScreenDeveloperPreview() {
    val viewModel = viewModel(FormEntryViewModel::class.java)
    AplikasiKPUTheme {
        FormEntryScreen(
            viewModel = viewModel
        )
    }
}


@Composable
fun FormEntryScreen(
    viewModel: FormEntryViewModel,
    modifier: Modifier = Modifier,
    mainEvent: (FormEntryActivityEvent) -> Unit = {},
) {
    val verticalScrollState = rememberScrollState()
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.isModalDatePickerShow) {
        DatePickerModal(
            onDismiss = viewModel::dismissDatePicker,
            onDateSelected = {
                it?.let {
                    viewModel.changeTanggalPendataanChanged(it)
                }
            }
        )
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 8.dp,
                    horizontal = 16.dp
                )
                .verticalScroll(verticalScrollState)
        ) {

            KPUOutlineTextField(
                label = "Nomor Induk Kependudukan",
                iconResId = R.drawable.ic_outline_badge_24,
                value = state.nik,
                onValueChange = viewModel::afterNikChanged,
                modifier = Modifier.fillMaxSize()
            )

            KPUOutlineTextField(
                label = "Nama",
                iconResId = R.drawable.ic_outline_person_24,
                value = state.namaLengkap,
                onValueChange = viewModel::afterNamaLengkapChanged,
                modifier = Modifier.fillMaxSize()
            )

            KPUOutlineTextField(
                label = "Nomor Handphone",
                iconResId = R.drawable.ic_baseline_phone_24,
                value = state.nomorHandphone,
                onValueChange = viewModel::afterNomorHandphoneChanged,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Jenis Kelamin"
            )

            Row {
                Row(
                    Modifier
                        .weight(0.5f)
                        .height(56.dp)
                        .selectable(
                            selected = ( state.gender == 1 ),
                            onClick = { viewModel.onGenderChanged(1) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = ( state.gender == 1 ),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = "Laki-laki",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }

                Row(
                    Modifier
                        .weight(0.5f)
                        .height(56.dp)
                        .selectable(
                            selected = ( state.gender == 2 ),
                            onClick = { viewModel.onGenderChanged(2) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = ( state.gender == 2 ),
                        onClick = null // null recommended for accessibility with screenreaders
                    )
                    Text(
                        text = "Perempuan",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }


            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    onClick = viewModel::showDatePicker,
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 16.dp
                            ),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_calendar_today_24),
                            contentDescription = "Icon Tanggal Pendataan",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = state.tanggalPendataanReadable,
                            maxLines = 1,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )

                    }
                }
            }

            Text(
                text = "Alamat"
            )
            // TextField Area
            OutlinedTextField(
                value = state.alamat,
                onValueChange = viewModel::afterAlamatChanged,
                minLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
            )

            KpuButton(
                text = "Ambil Lokasi Sekarang",
                onClick = {
                    mainEvent(FormEntryActivityEvent.GoToMaps)
                }
            )

            Text(
                text = "Gambar"
            )

            KpuButton(
                text = "Simpan",
                onClick = {
                    // TODO
                }
            )


        }
    }
}

@Composable
fun KPUOutlineTextField(
    label : String,
    value: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    onValueChange : (String) -> Unit
) {
    OutlinedTextField(
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Icon $label",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp)
                )
                Text(
                    text = label,
                    maxLines = 1,
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )

            }
        },
        value = value,
        maxLines = 1,
        singleLine = true,
        onValueChange = onValueChange,
        modifier = modifier.minimumInteractiveComponentSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Batal")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}