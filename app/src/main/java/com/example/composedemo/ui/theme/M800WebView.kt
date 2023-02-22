package com.example.composedemo.ui.theme

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebView(url: String) {
    Surface(color = MaterialTheme.colors.background) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                // 創建一個 WebView，設置 WebViewClient 來處理網頁載入事件
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                            // 在 WebView 中載入指定的 URL，而不是開啟系統預設瀏覽器
                            view.loadUrl(url)
                            return true
                        }
                    }
                }
            },
            update = { webView ->
                // 在 AndroidView 的 update 函數中設置 WebView 的屬性及載入指定 URL 的網頁
                webView.loadUrl(url)
            }
        )
    }
}