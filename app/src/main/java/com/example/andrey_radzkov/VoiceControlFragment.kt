package com.example.andrey_radzkov

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


/**
 * @author Radzkov Andrey
 */
private const val SPEECH_REQUEST_CODE = 0

class VoiceControlFragment : Fragment(), MediaPlayer.OnCompletionListener {

    lateinit var buttonRecord: Button
    lateinit var statusText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_voice_control, container, false)
        statusText = rootView.findViewById(R.id.statusText) as TextView
        buttonRecord = rootView.findViewById(R.id.record) as Button
        statusText.setText("Ready")

        buttonRecord.setOnClickListener {
            displaySpeechRecognizer()
            buttonRecord.setEnabled(false)
        }

        return rootView
    }

    override fun onCompletion(mp: MediaPlayer) {
        buttonRecord.setEnabled(true)
        statusText.setText("Ready")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Voice control"
    }


    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }
        // Start the activity, the intent will be populated with the speech text
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val spokenText: String? =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                        results[0]//TODO: analyze all
                    }
            statusText.text = spokenText
            val handler = Handler()
            handler.postDelayed({
                statusText.text = "Will open Supplyon in a second..."
                val handlerOpen = Handler()
                handlerOpen.postDelayed({
                    if (spokenText == "open supplyon") {
                        val browse = Intent(Intent.ACTION_VIEW, Uri.parse("http://evbyminsd2156.minsk.epam.com/slm/"))
                        ContextCompat.startActivity(context!!, browse, null)
                    }
                }, 1500)
            }, 1500)


        } else {
            statusText.text = "No text, please, try again"
        }

        buttonRecord.setEnabled(true)
        super.onActivityResult(requestCode, resultCode, data)
    }
}