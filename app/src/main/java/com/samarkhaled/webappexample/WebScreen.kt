package com.samarkhaled.webappexample

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.samarkhaled.webappexample.ui.theme.WebAppExampleTheme


@Composable
fun WebScreen() {

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
            /*
            This property in Android WebView determines whether the WebView will show a preview of
            the web page before it’s completely loaded. When set to true, the WebView displays
             a thumbnail of the web page during the loading process. This feature enhances the user
             experience by giving them a visual preview of the web page’s content as it loads,
             helping them understand the page’s context.
             */
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)

            println("Before update userAgent = " + settings.userAgentString)
            val manager = context?.packageManager
            val info = manager?.getPackageInfo(
                context?.packageName.toString(), 0
            )
            val versionName = info?.versionName
            settings.userAgentString = "Web App $versionName"

            println("After update userAgent = " + settings.userAgentString)

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
fun WebScreenPreview() {
    WebAppExampleTheme {
        WebScreen()
    }
}