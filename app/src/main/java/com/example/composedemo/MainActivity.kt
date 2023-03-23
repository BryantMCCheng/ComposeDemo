package com.example.composedemo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    modifier: Modifier = Modifier
) {
    Scaffold(topBar = {
        TopAppBarField()
    }) {
        LazyColumn(modifier = Modifier.padding(20.dp)) {
            item {
                MessageField()
                Spacer(modifier = Modifier.height(10.dp))
                AttachmentField()
                Spacer(modifier = Modifier.height(10.dp))
                PrivacyTermsField()
            }
        }
        WebViewField()
    }
}