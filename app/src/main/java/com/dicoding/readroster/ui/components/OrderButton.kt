package com.dicoding.readroster.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dicoding.readroster.ui.theme.ReadRosterTheme

@Composable
fun OrderButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "Order Button"
            }
    ) {
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 26.sp,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun OrderButtonPreview() {
    ReadRosterTheme {
        OrderButton(
            text = "Add to Cart",
            onClick = {}
        )
    }
}