package com.example.andrey_radzkov

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView


/**
 * @author Radzkov Andrey
 */
class ServiceStateFragment : Fragment() {
    lateinit var buttonSwitchMode: Button
    lateinit var buttonSwitchBackground: Button
    lateinit var buttonLogin: Button
    lateinit var buttonSlm: Button
    lateinit var header: TextView
    lateinit var layout: RelativeLayout
    var isBlack: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_service_state, container, false)
        buttonSwitchMode = rootView.findViewById(R.id.switchMode) as Button
        buttonSwitchBackground = rootView.findViewById(R.id.switchBackground) as Button
        buttonLogin = rootView.findViewById(R.id.loginService)
        buttonSlm = rootView.findViewById(R.id.slmService)
        header = rootView.findViewById(R.id.serviceStateText)
        layout = rootView.findViewById(R.id.serviceBackground)
        buttonSwitchMode.setOnClickListener {
            //TODO: too dirty, fix it
            buttonLogin.rotationX += 180f
            buttonSlm.rotationX += 180f
            header.rotationX += 180f
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
        if (savedInstanceState != null) {
            val rotationX = savedInstanceState.getFloat("rotationX")
            buttonLogin.rotationX = rotationX
            buttonSlm.rotationX = rotationX
            header.rotationX = rotationX
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putFloat("rotationX", buttonLogin.rotationX)
    }

}