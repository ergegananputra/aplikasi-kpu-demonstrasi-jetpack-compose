package com.ergegananputra.aplikasikpu.ui.presentations.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ergegananputra.aplikasikpu.R

@Preview
@Composable
private fun KpuButtonPreview() {
    KpuButton(
        text = "Informasi",
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun KpuButton(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    enabled : Boolean = true,
    onClick : () -> Unit = {},
    textALign : TextAlign = TextAlign.Left,
    lead: @Composable RowScope.() -> Unit = {},
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
            .minimumInteractiveComponentSize()
            .heightIn(max= 56.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { if (enabled) onClick() },
        color = if (enabled) color else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                )
        ) {
            lead()
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = textALign,
                modifier = Modifier.weight(1f)
            )
        }
    }
}