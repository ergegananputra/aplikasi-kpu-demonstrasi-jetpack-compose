package com.ergegananputra.aplikasikpu.ui.presentations.lihatdata

import android.content.Context
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu.domain.AppContainer
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.domain.repository.DataPesertaRepository
import com.ergegananputra.aplikasikpu.ui.presentations.components.DataPesertaItem
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import com.ergegananputra.aplikasikpu.utils.toSimpleReadableString
import kotlinx.coroutines.flow.combineTransform
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DataPemilihScreenDeveloperPreview() {
    val viewModel = DataPemilihViewModel(
        appContainer = object : AppContainer {
            override val dataPesertaRepository: DataPesertaRepository
                get() = TODO("Not yet implemented")
            override val context: Context
                get() = TODO("Not yet implemented")
        }
    )
    AplikasiKPUTheme {
        DataPemilihScreen(viewModel = viewModel)
    }
}


@Composable
fun DataPemilihScreen(
    viewModel: DataPemilihViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {


            LazyColumn(
                state = lazyListState,
            ){
                items(
                    items = state.dataPesertaList,
                    key = { it.id }
                ) {dataPeserta ->
                    DataPesertaItem(dataPeserta)
                }

                item { Spacer(modifier = Modifier.size(32.dp)) }

            }


        }
    }
}


