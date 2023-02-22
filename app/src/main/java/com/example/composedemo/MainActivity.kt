package com.example.composedemo

import WebBrowser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                Column(modifier = Modifier.padding(20.dp)) {
                    MessageField()
                    Spacer(modifier = Modifier.height(10.dp))
                    AttachmentField()
                    Spacer(modifier = Modifier.height(10.dp))
                    PrivacyPolicyText()
//                    WebBrowser(url = "https://www.google.com.tw")
                }
            }
        }
    }
}

@Composable
fun MessageField() {
    val hint = stringResource(
        id = R.string.lc_feedback_edittext_hint,
        stringResource(id = R.string.lc_application_app_name)
    )
    var text by remember { mutableStateOf("") }
    val maxLength = 300
    MaxLengthEditTextWithCounter(
        maxLength = maxLength,
        hint = hint,
        onValueChange = {
            text = it
        }
    )
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
        var isTextFieldFocused by remember { mutableStateOf(false) }
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = (5 * (MaterialTheme.typography.body1.fontSize.value + 8)).dp)
                .border(
                    width = 1.dp,
                    color = if (isTextFieldFocused) Color.Blue else Color(0xffededed),
                    shape = RoundedCornerShape(4.dp)
                )
                .onFocusChanged { isTextFieldFocused = it.isFocused },
            value = text,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    text = newValue
                    onValueChange(newValue)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = textStyle.copy(color = textColor),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            ),
            placeholder = {
                if (text.isEmpty()) {
                    Text(
                        text = hint,
                        style = textStyle.copy(color = hintColor)
                    )
                }
            }
        )
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

@Composable
fun PrivacyTermsField() {

}

@Preview(showBackground = true)
@Composable
fun PrivacyTermsPreview() {
    ComposeDemoTheme {
        PrivacyPolicyText()
    }
}

data class HyperlinkInfo(
    var text: String,
    var link: String,
    var action: (HyperlinkInfo) -> Unit,
)

@Composable
fun PrivacyPolicyText() {
    val fullText = stringResource(
        id = R.string.lc_feedback_privacy_service,
        stringResource(id = R.string.lc_application_app_name)
    )
    val hyperlinkInfoList = listOf(
        HyperlinkInfo(
            text = stringResource(id = R.string.lc_feedback_privacy_policy),
            link = stringResource(id = R.string.m800_privacy_link),
            action = {
//                WebBrowser(url = it.text)
            }

        ),
        HyperlinkInfo(
            text = stringResource(id = R.string.lc_feedback_terms_service),
            link = stringResource(id = R.string.m800_terms_service_link),
            action = {
//                WebBrowser(url = it.text)
            }
        )
    )
    HyperlinkText(
        fullText = fullText,
        hyperlinkInfoList = hyperlinkInfoList,
        linkTextDecoration = TextDecoration.None
    )
}

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    hyperlinkInfoList: List<HyperlinkInfo>,
    fullText: String,
    linkTextColor: Color = Color(0xff4a90e2),
    linkTextFontWeight: FontWeight = FontWeight.Normal,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)
        hyperlinkInfoList.forEach { info ->
            val startIndex = fullText.indexOf(info.text)
            val endIndex = startIndex + info.text.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = info.link,
                start = startIndex,
                end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}




