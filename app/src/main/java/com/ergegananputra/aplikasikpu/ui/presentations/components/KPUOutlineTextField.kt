package com.ergegananputra.aplikasikpu.ui.presentations.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ergegananputra.aplikasikpu.R

@Composable
fun KPUOutlineTextField(
    label : String,
    value: String,
    iconResId: Int,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    errMsg : String? = null,
    imeAction: ImeAction = ImeAction.Next,
    onValueChange : (String) -> Unit
) {
    var overrideTransformation by rememberSaveable { mutableStateOf(false) }
    val targetKeyboardTypeOverride = VisualTransformation.None

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
        enabled = enabled,
        isError = errMsg != null,
        supportingText = {
            errMsg?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        value = value,
        maxLines = 1,
        singleLine = true,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        visualTransformation = if (overrideTransformation) {
            targetKeyboardTypeOverride
        } else {
            when (keyboardType) {
                KeyboardType.Password -> PasswordVisualTransformation()
                else -> VisualTransformation.None
            }
        },
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(
                    onClick = {
                        overrideTransformation = !overrideTransformation
                    },
                ) {
                    Icon(
                        painter = if (overrideTransformation) {
                            painterResource(id = R.drawable.ic_baseline_visibility_24)
                        } else {
                            painterResource(id = R.drawable.ic_baseline_visibility_off_24)
                        },
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        },
        modifier = modifier.minimumInteractiveComponentSize()
    )
}