package com.example.composedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.ui.theme.ComposeDemoTheme

@Composable
fun AttachmentField() {
    Row(
        modifier = Modifier
            .height(335.dp)
            .fillMaxWidth()
            .background(color = Color.Cyan)
    ) {
        Text(
            text = "Attachment",
            color = Color(0xff212121),
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AttachmentPreview() {
    ComposeDemoTheme {
        AttachmentField()
    }
}