package com.ergegananputra.aplikasikpu.ui.presentations.maps

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ergegananputra.aplikasikpu.ui.navigations.FormEntryActivityEvent
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState


@Preview
@Composable
private fun MapsScreenDeveloperPreview() {
    AplikasiKPUTheme {
        MapsScreen(
            mainEvent = {},
            viewModel = viewModel(MapsViewModel::class),
        )
    }
}


@Composable
fun MapsScreen(
    mainEvent: (FormEntryActivityEvent) -> Unit,
    viewModel: MapsViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.currentLocation, 50f)
    }

    LaunchedEffect(
        key1 = state.latitude,
        key2 = state.longitude
    ) {
        cameraPositionState.position = CameraPosition.fromLatLngZoom(state.currentLocation, 50f)
    }


    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapLongClick = {
                mainEvent(FormEntryActivityEvent.UpdateLocation(it))
                             },
            onMapLoaded = {
                mainEvent(FormEntryActivityEvent.GetCurrentLocation)
            },
            modifier = Modifier
                .fillMaxSize(),
        )

        Column(
            Modifier
                .zIndex(2f)
                .align(Alignment.TopCenter)
                .padding(
                    vertical = 24.dp,
                    horizontal = 16.dp
                )
                .fillMaxWidth()
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = state.address,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier= Modifier.weight(0.6f))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        mainEvent(FormEntryActivityEvent.BackToForm(state.address))
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(0.4f)
                ) {
                    Text("Simpan")
                }
            }

        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            onClick = {
                mainEvent(FormEntryActivityEvent.GoToMaps)
            },
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomStart)
                .padding(
                    vertical = 24.dp,
                    horizontal = 16.dp
                )
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "My Location",
                modifier = Modifier.padding(24.dp)
            )
        }
    }
}
