package com.ergegananputra.aplikasikpu.ui.presentations.components


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ergegananputra.aplikasikpu.R

@Preview
@Composable
private fun LogOutAlertDialogPreview() {
    LogOutAlertDialog(
        onDismissRequest = {},
        onConfirmation = {}
    )
}


@Composable
fun LogOutAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            Icon(painterResource(R.drawable.ic_baseline_exit_to_app_24), contentDescription = "Dialog Icon")
        },
        title = {
            Text(text = "Keluar")
        },
        text = {
            Text(text = "Apakah Anda yakin ingin keluar?")
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                colors =  ButtonDefaults.textButtonColors().copy(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Keluar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Kembali ke Aplikasi")
            }
        }
    )
}