package com.ergegananputra.aplikasikpu.ui.presentations.informasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ergegananputra.aplikasikpu.R

@Composable
fun InformasiScreen(innerPadding: PaddingValues) {
    val verticalScroll = rememberScrollState()
    Surface(
        modifier = Modifier.padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(verticalScroll)
        ) {
            Text(
                text = stringResource(R.string.deskripsi_kpu),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            )

            Image(
                painter = painterResource(R.drawable.logo_kpu_sample),
                contentDescription = "Logo KPU",
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(32.dp)
                    .sizeIn(minHeight = 150.dp)
            )

            Text(
                text = "Dibuat oleh Adiel Boanerge G. 2024.",
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            )

            Text(
                text = stringResource(R.string.lorem_ipsum_paragraph),
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
            )
        }
    }
}