package com.example.andrey_radzkov

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout


/**
 * @author Radzkov Andrey
 */
class ServiceStateFragment : Fragment() {
    var lv: ListView? = null

    lateinit var buttonSwitchMode: Button
    lateinit var buttonSwitchBackground: Button
    lateinit var buttonLogin: Button
    lateinit var buttonSlm: Button
    lateinit var layout: RelativeLayout
    private lateinit var webView: WebView
    var isBlack: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_service_state, container, false)
        buttonSwitchMode = rootView.findViewById(R.id.switchMode) as Button
        buttonSwitchBackground = rootView.findViewById(R.id.switchBackground) as Button
        buttonLogin = rootView.findViewById(R.id.loginService)
        buttonSlm = rootView.findViewById(R.id.slmService)
        layout = rootView.findViewById(R.id.serviceBackground)
        buttonSwitchMode.setOnClickListener {
            //TODO: too dirty, fix it
            buttonLogin.rotationX += 180f
            buttonSlm.rotationX += 180f
        }
        buttonSwitchBackground.setOnClickListener {
            if (isBlack) {
                layout.setBackgroundColor(Color.WHITE)
                isBlack = false
            } else {
                layout.setBackgroundColor(Color.BLACK)
                isBlack = true
            }
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Services state"
        webView = activity!!.findViewById(R.id.webView2)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = BlogFragment.MyWebViewClient(null, webView, activity!!)
        webView.setWebChromeClient(WebChromeClient())
        webView.loadUrl("https://shop.exchange.se.com/api/internal/storefront/v1/cta?productId=77268&type=BUY")

        if (savedInstanceState != null) {
            val rotationX = savedInstanceState.getFloat("rotationX")
            val color = savedInstanceState.getInt("color")
            buttonLogin.rotationX = rotationX
            buttonSlm.rotationX = rotationX
            layout.setBackgroundColor(color)
            if (color == Color.BLACK) {
                isBlack = true
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("rotationX", buttonLogin.rotationX)
        outState.putInt("color", if (isBlack) Color.BLACK else Color.WHITE)
    }

}