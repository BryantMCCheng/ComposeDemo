package com.example.composedemo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composedemo.ui.theme.ComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeDemoTheme {
                MainView()
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainView(
    modifier: Modifier = Modifier,
    feedbackViewModel: FeedbackViewModel = viewModel()
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            if (feedbackViewModel.isWebOpened.collectAsState(initial = false).value) {
                TopAppBarField(onBackAction = {
                    feedbackViewModel.closeAll()
                })
            } else {
                TopAppBarField(onBackAction = {
                    (context as? Activity)?.finish()
                })
            }
        },
        content = {
            Column(modifier = Modifier.padding(20.dp)) {
                MessageField()
                Spacer(modifier = Modifier.height(10.dp))
                AttachmentField()
                Spacer(modifier = Modifier.height(10.dp))
                PrivacyTermsField(
                    onClickPrivacyPolicy = { feedbackViewModel.openPrivacyPolicy() },
                    onClickTermsOfService = { feedbackViewModel.openTermsOfService() })
            }
            when (feedbackViewModel.privacyPolicyDisplayState.collectAsState().value) {
                true -> {
                    WebView(
                        url = stringResource(id = R.string.m800_privacy_link),
                        onBack = { webView ->
                            if (webView?.canGoBack() == true) {
                                webView.goBack()
                            } else {
                                feedbackViewModel.closePrivacyPolicy()
                            }
                        })
                }
                false -> {
                    // do nothings
                }
            }
            when (feedbackViewModel.termsOfServiceDisplayState.collectAsState().value) {
                true -> {
                    WebView(
                        url = stringResource(id = R.string.m800_terms_service_link),
                        onBack = { webView ->
                            if (webView?.canGoBack() == true) {
                                webView.goBack()
                            } else {
                                feedbackViewModel.closeTermsOfService()
                            }
                        })
                }
                false -> {
                    // do nothings
                }
            }
        }
    )
}