package com.ergegananputra.aplikasikpu.ui.presentations.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun ConfirmationAlertDialogPreview() {
    ConfirmationAlertDialog(
        "Alert Dialog",
        onDismissRequest = {},
        onConfirmation = {}
    )
}


@Composable
fun ConfirmationAlertDialog(
    title: String,
    text: String = "Apakah Anda yakin?",
    confirmationText: String = "Ya",
    dismissText: String = "Tidak",
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    imagePainter: ImageVector? = null,

    ) {
    AlertDialog(
        icon = {
            imagePainter?.let {
                Icon(imagePainter, contentDescription = "Dialog Icon")
            }
        },
        title = {
            Text(text = title)
        },
        text = {
            Text(text = text)
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
                Text(confirmationText)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(dismissText)
            }
        }
    )
}