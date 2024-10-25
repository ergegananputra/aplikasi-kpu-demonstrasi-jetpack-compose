package com.ergegananputra.aplikasikpu.ui.presentations.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.ergegananputra.aplikasikpu.R
import com.ergegananputra.aplikasikpu.domain.entities.room.DataPeserta
import com.ergegananputra.aplikasikpu.utils.toSimpleReadableString


@Composable
fun DataPesertaItem(dataPeserta: DataPeserta) {


    var isImageCollapse by remember {
        mutableStateOf(true)
    }

    val heightDyn by animateDpAsState(
        targetValue = if (isImageCollapse) 100.dp else 700.dp,
        label = "Height Dynamic - ${dataPeserta.id}"
    )


    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        onClick = {
            isImageCollapse = !isImageCollapse
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = if (isImageCollapse) painterResource(id = R.drawable.baseline_keyboard_arrow_down_24) else painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                contentDescription = "Arrow Drop Down",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(14.dp)
                    .size(36.dp)
                    .zIndex(2f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    )
                    .align(Alignment.Center)
                    .zIndex(1f)
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
                            text = dataPeserta.tanggalPendataan.toSimpleReadableString(),
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
                        text = dataPeserta.namaLengkap ?: "Tidak ada nama lengkap",
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
                            text = dataPeserta.nik ?: "Tidak ada nik",
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
                            text = dataPeserta.nomorHandphone ?: "-",
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
                        text = dataPeserta.alamat ?: "Tidak ada alamat",
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
                    model = "https://masmoendigital.store/storage/${dataPeserta.imageUrl}",
                    contentDescription = "Bukti Pendataan ${dataPeserta.id}",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp, max = heightDyn)
                        .padding(
                            vertical = 8.dp
                        )
                        .clip(RoundedCornerShape(24.dp))
                )


            }
        }
    }
}