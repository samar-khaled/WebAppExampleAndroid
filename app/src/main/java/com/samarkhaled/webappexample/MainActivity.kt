package com.samarkhaled.webappexample

import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.samarkhaled.webappexample.ui.theme.WebAppExampleTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebAppExampleTheme {
                WebView()
            }
        }
    }
}

@Composable
fun WebView() {

    var backEnabled by remember { mutableStateOf(false) }
    var webView: WebView? = null

    val mUrl = "https://www.google.com"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            this.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)

            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    backEnabled = view.canGoBack()
                }

            }
            loadUrl(mUrl)
            webView = this
        }
    }, update = {
        webView = it
    })

    BackHandler(enabled = backEnabled) {
        webView?.goBack()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WebAppExampleTheme {
        WebView()
    }
}