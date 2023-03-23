package com.example.composedemo

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composedemo.ui.theme.ComposeDemoTheme

data class HyperlinkInfo(
    var text: String,
    var link: String,
    var action: (HyperlinkInfo) -> Unit,
)

@Composable
fun PrivacyTermsField(feedbackViewModel: FeedbackViewModel = viewModel()) {
    PrivacyPolicyText(
        { feedbackViewModel.openPrivacyPolicy() },
        { feedbackViewModel.openTermsOfService() }
    )
}

@Preview(showBackground = true)
@Composable
fun PrivacyTermsPreview() {
    ComposeDemoTheme {
        PrivacyPolicyText()
    }
}

@Composable
fun PrivacyPolicyText(
    onClickPrivacyPolicy: (() -> Unit)? = null, onClickTermsOfService: (() -> Unit)? = null
) {
    val fullText = stringResource(
        id = R.string.lc_feedback_privacy_service,
        stringResource(id = R.string.lc_application_app_name)
    )
    val hyperlinkInfoList =
        listOf(HyperlinkInfo(text = stringResource(id = R.string.lc_feedback_privacy_policy),
            link = stringResource(id = R.string.m800_privacy_link),
            action = {
                onClickPrivacyPolicy?.invoke()
            }

        ), HyperlinkInfo(text = stringResource(id = R.string.lc_feedback_terms_service),
            link = stringResource(id = R.string.m800_terms_service_link),
            action = {
                onClickTermsOfService?.invoke()
            })
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
                ), start = startIndex, end = endIndex
            )
            addStringAnnotation(
                tag = "KEY", annotation = info.text, start = startIndex, end = endIndex
            )
        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ), start = 0, end = fullText.length
        )
    }

    ClickableText(modifier = modifier, text = annotatedString, onClick = {
        annotatedString.getStringAnnotations("KEY", it, it).firstOrNull()?.let { stringAnnotation ->
            hyperlinkInfoList.forEach { info ->
                if (stringAnnotation.item == info.text) {
                    info.action.invoke(info)
                }
            }
        }
    })
}