package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu._dev.Mock.Companion.appContainer
import com.ergegananputra.aplikasikpu.ui.navigations.DataPemilihActivityEvent
import com.ergegananputra.aplikasikpu.ui.presentations.components.ConfirmationAlertDialog
import com.ergegananputra.aplikasikpu.ui.presentations.components.DataPesertaItem
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DataPemilihScreenDeveloperPreview() {
    val viewModel = DataPemilihViewModel(
        appContainer = appContainer
    )
    AplikasiKPUTheme {
        DataPemilihScreen(viewModel = viewModel)
    }
}


@Composable
fun DataPemilihScreen(
    viewModel: DataPemilihViewModel,
    modifier: Modifier = Modifier,
    mainEvent : (DataPemilihActivityEvent) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

        state.deleteId?.let {
            ConfirmationAlertDialog(
                imagePainter = Icons.Filled.Delete,
                title = "Hapus Data",
                text = "Apakah Anda yakin ingin menghapus data ini?",
                onDismissRequest = { viewModel.hideDeleteConfirmation() },
                onConfirmation = { viewModel.deleteDataPeserta(it) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {

            OutlinedTextField(
                label = {
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_search_24),
                            contentDescription = "Icon Search",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .size(24.dp)
                        )
                        Text(
                            text = "Pencarian",
                            maxLines = 1,
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        )
                    }

                },
                placeholder = {
                    Text(
                        text = "Masukkan NIK atau Nama Lengkap",
                        maxLines = 1,
                    )
                },
                value = state.keyword,
                maxLines = 1,
                singleLine = true,
                onValueChange = viewModel::onSearchKeywordChanged,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                trailingIcon = {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_clear_24),
                        contentDescription = "Clear",
                        modifier = Modifier
                            .clickable {
                                if (state.keyword.isNotEmpty()) {
                                    viewModel.onSearchKeywordChanged("")
                                } else {
                                    focusManager.clearFocus()
                                }
                            }
                            .size(24.dp),
                    )

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .weight(1f)
            ){
                items(
                    items = state.dataPesertaList,
                    key = { it.id }
                ) {dataPeserta ->
                    DataPesertaItem(
                        dataPeserta = dataPeserta,
                        onClick = {
                            mainEvent(DataPemilihActivityEvent.OnDetail(it))
                        },
                        onDeleteClick = {
                            viewModel.showDeletePeserta(it)
                        }
                    )
                }

                item { Spacer(modifier = Modifier.size(32.dp)) }

            }


        }
    }
}


