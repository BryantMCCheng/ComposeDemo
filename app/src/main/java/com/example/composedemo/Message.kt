package com.example.composedemo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composedemo.ui.theme.ComposeDemoTheme

@Composable
fun MessageField(feedbackViewModel: FeedbackViewModel = viewModel()) {
    val hint = stringResource(
        id = R.string.lc_feedback_edittext_hint,
        stringResource(id = R.string.lc_application_app_name)
    )
    val maxLength = 300
    MaxLengthEditTextWithCounter(maxLength = maxLength, hint = hint, onValueChange = {
        feedbackViewModel.updateInput(it)
        Log.e("zxc", "input msg = $it")
    })
}

@Composable
fun MaxLengthEditTextWithCounter(
    maxLength: Int,
    hint: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
    textColor: Color = Color.Black,
    hintColor: Color = Color(0xffbababa),
) {
    Column {
        Text(
            text = "Message",
            color = Color(0xff212121),
            textAlign = TextAlign.Start,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        var text by remember { mutableStateOf("") }
        OutlinedTextField(modifier = modifier
            .fillMaxWidth()
            .heightIn(min = (5 * (MaterialTheme.typography.body1.fontSize.value + 8)).dp),
            value = text,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    text = newValue
                    onValueChange(newValue)
                }
            },
            textStyle = textStyle.copy(color = textColor),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            placeholder = {
                if (text.isEmpty()) {
                    Text(
                        text = hint, style = textStyle.copy(color = hintColor)
                    )
                }
            })
        Spacer(modifier = Modifier.height(10.dp))
        val currentLength = text.length
        Text(
            text = "$currentLength/$maxLength",
            color = Color(0xff646464),
            modifier = Modifier.align(Alignment.End),
            fontSize = 12.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    ComposeDemoTheme {
        MessageField()
    }
}