package com.example.andrey_radzkov

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ListView
import com.example.andrey_radzkov.model.BlogListViewAdapter
import com.example.andrey_radzkov.model.getArticles


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
//        fab.onClickListener(View.OnClickListener { lv!!.setAdapter(adapter) })

        return inflate
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Supplyon"
        webView =   activity!!.findViewById(R.id.webView1)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
//        webView.loadUrl("http://epbyminw3508.minsk.epam.com:19080/logon/logonServlet")
        webView.loadUrl("https://google.com")
    }
}