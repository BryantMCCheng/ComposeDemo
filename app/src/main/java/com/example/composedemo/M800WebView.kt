package com.example.composedemo

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WebViewField(feedbackViewModel: FeedbackViewModel = viewModel()) {
    if (feedbackViewModel.privacyPolicyDisplayState.collectAsState().value) {
        WebView(url = stringResource(id = R.string.m800_privacy_link), onBack = { webView ->
            if (webView?.canGoBack() == true) {
                webView.goBack()
            } else {
                feedbackViewModel.closePrivacyPolicy()
            }
        })
    }
    if (feedbackViewModel.termsOfServiceDisplayState.collectAsState().value) {
        WebView(url = stringResource(id = R.string.m800_terms_service_link), onBack = { webView ->
            if (webView?.canGoBack() == true) {
                webView.goBack()
            } else {
                feedbackViewModel.closeTermsOfService()
            }
        })
    }
}

@Composable
fun WebView(
    url: String, onBack: (webView: WebView?) -> Unit
) {
    var webView: WebView? = null
    Surface(color = MaterialTheme.colors.background) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = { context ->
            // Create a WebView and set up WebViewClient to handle web page load events
            WebView(context).apply {
                webView = this
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        // Load the specified URL in the WebView instead of opening the system's default browser
                        view.loadUrl(url)
                        return true
                    }
                }
            }
        }, update = { webView ->
            // Set the properties of the WebView in the update function of the AndroidView and load the web page with the specified URL.
            webView.loadUrl(url)
        })
        BackHandler {
            onBack(webView)
        }
    }
}