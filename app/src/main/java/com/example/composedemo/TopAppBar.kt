package com.example.composedemo

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TopAppBarField(feedbackViewModel: FeedbackViewModel = viewModel()) {
    val context = LocalContext.current
    TopAppBar(title = {
        if (feedbackViewModel.privacyPolicyDisplayState.collectAsState().value) {
            Text(text = "Privacy Policy")
        } else if (feedbackViewModel.termsOfServiceDisplayState.collectAsState().value) {
            Text(text = "Terms Of Service")
        } else {
            Text(text = "Feedback Form")
        }
    }, actions = {
        if (!feedbackViewModel.isWebOpened.collectAsState(initial = false).value) {
            IconButton(onClick = {
                Log.d("zxc", "menu clicked")
            }) {
                Icon(
                    Icons.Default.Send,
                    contentDescription = stringResource(id = R.string.lc_feedback_send)
                )
            }
        }
    }, navigationIcon = {
        val onBackAction =
            if (feedbackViewModel.isWebOpened.collectAsState(initial = false).value) {
                { feedbackViewModel.closeAll() }
            } else {
                { (context as? Activity)?.finish() }
            }
        IconButton(onClick = {
            Log.d("zxc", "onBackPressed")
            onBackAction.invoke()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "back")
        }
    }, backgroundColor = MaterialTheme.colors.primary, modifier = Modifier.fillMaxWidth()
    )
}