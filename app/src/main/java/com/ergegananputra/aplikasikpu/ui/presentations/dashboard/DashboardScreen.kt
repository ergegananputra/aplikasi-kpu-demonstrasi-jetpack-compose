package com.ergegananputra.aplikasikpu.ui.presentations.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ergegananputra.aplikasikpu.ui.theme.AplikasiKPUTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun DashboardScreenDeveloperPreview() {
    AplikasiKPUTheme {
        DashboardScreen()
    }
}


@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {

    }
}