package com.example.andrey_radzkov

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.net.http.SslError
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ListView
import com.example.andrey_radzkov.model.BlogListViewAdapter
import com.example.andrey_radzkov.model.getArticles
import okhttp3.OkHttpClient
import okhttp3.Request


/**
 * @author Radzkov Andrey
 */
class BlogFragment : Fragment() {

    var lv: ListView? = null
    var adapter: BlogListViewAdapter? = null
    private lateinit var webView: WebView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_blog, container, false)
        adapter = BlogListViewAdapter(context!!, getArticles())
        lv = inflate.findViewById(R.id.lv) as ListView
        lv!!.adapter = adapter
        lv!!.visibility = android.view.View.GONE

        return inflate
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Supplyon"
        webView = activity!!.findViewById(R.id.webView1)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = MyWebViewClient(lv!!, webView, activity!!)
        webView.setWebChromeClient(WebChromeClient())
        webView.loadUrl("https://google.com")
//        webView.loadUrl("https://se.com")
    }

    public class MyWebViewClient(private var lv: ListView?, private var webView: WebView, private var activity: FragmentActivity) : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
//
            webView.evaluateJavascript("function hello(){alert('hello $url from custom js function. Selected: ' + document.querySelector('.radio-button .radio-button-input:checked').getAttribute('id'));} hello();"
            ) { token ->
//                    val tokenWithoutQuates = token.replace("\"", "")
//                    val messageCount = GetSocialMessageTask(tokenWithoutQuates).execute()
//                    lv.visibility = android.view.View.VISIBLE
//                    webView.visibility = android.view.View.GONE
//                    val dialog = getAlertDialog(tokenWithoutQuates)
//
//                    dialog.setMessage(messageCount.get())
//                    dialog.show()
            }
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, er: SslError?) {
            handler.proceed()
            // Ignore SSL certificate errors
        }

        private fun getAlertDialog(token: String): AlertDialog {
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Messages: ")
            builder.setPositiveButton("Close") { dialog2, id ->
                // we need to exit here
                webView.loadUrl(" http://epbyminw2336.minsk.epam.com/logon/LogoutServlet")
                dialog2.dismiss()
            }
            builder.setNegativeButton("Recall") { dialog2, id ->
                val messageCount2 = GetSocialMessageTask(token).execute()
                dialog2.dismiss()
                val dialog = getAlertDialog(token)
                dialog.setMessage(messageCount2.get())
                dialog.show()
            }
            val dialog = builder.create()
            return dialog
        }
    }

    private class GetSocialMessageTask(val token: String) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg urls: Void): String {

            val client = OkHttpClient()
            val request = Request.Builder()
                    .url("http://epbyminw3508.minsk.epam.com:8080/social/user/messages/unread-messages-count-test?access_token=" + token)
                    .build()
            try {
                client.newCall(request).execute().use { response ->
                    return "count: " + response.body()!!.string()
                }
            } catch (e: Exception) {
                return "error" + Math.random()
            }
        }
    }
}