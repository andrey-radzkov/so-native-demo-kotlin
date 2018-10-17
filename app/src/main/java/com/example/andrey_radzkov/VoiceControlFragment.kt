package com.example.andrey_radzkov

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.io.File
import java.io.IOException


/**
 * @author Radzkov Andrey
 */
private const val SPEECH_REQUEST_CODE = 0

class VoiceControlFragment : Fragment(), MediaPlayer.OnCompletionListener {

    lateinit var recorder: MediaRecorder
    lateinit var player: MediaPlayer
    lateinit var file: File
    lateinit var buttonRecord: Button
    lateinit var buttonStop: Button
    lateinit var buttonPlay: Button
    lateinit var statusText: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_voice_control, container, false)
        displaySpeechRecognizer()
        statusText = rootView.findViewById(R.id.statusText) as TextView
        buttonRecord = rootView.findViewById(R.id.record) as Button
        buttonStop = rootView.findViewById(R.id.stop) as Button
        buttonPlay = rootView.findViewById(R.id.play) as Button
        statusText.setText("Ready")
        buttonPlay.setEnabled(false)
        buttonStop.setEnabled(false)

        buttonRecord.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(rootView.context, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.RECORD_AUDIO),
                        10);
            }
            recorder = MediaRecorder()
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            val path = File(Environment.getExternalStorageDirectory().getPath())
            try {
//                file = File.createTempFile("temporary", ".3gp", path) TODO: investigate
                file = File.createTempFile("temporary", ".3gp", rootView.context.filesDir)
            } catch (e: IOException) {
            }

            recorder.setOutputFile(file.absolutePath)
            try {
                recorder.prepare()
            } catch (e: IOException) {
            }

            recorder.start()
            statusText.text = "Recording"
            buttonRecord.setEnabled(false)
            buttonPlay.setEnabled(false)
            buttonStop.setEnabled(true)

        }

        buttonStop.setOnClickListener {
            recorder.stop()
            recorder.release()
            player = MediaPlayer()
            player.setOnCompletionListener(this)
            try {
                player.setDataSource(file.absolutePath)
            } catch (e: IOException) {
            }

            try {
                player.prepare()
            } catch (e: IOException) {
            }

            buttonRecord.setEnabled(true)
            buttonStop.setEnabled(false)
            buttonPlay.setEnabled(true)
            statusText.text = "Ready to play"
        }

        buttonPlay.setOnClickListener {

            val am = rootView.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager?
            am!!.setStreamVolume(AudioManager.STREAM_MUSIC, am.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0)

            player.start()
            buttonRecord.setEnabled(false)
            buttonStop.setEnabled(false)
            buttonPlay.setEnabled(false)
            statusText.setText("Playing")
        }


        return rootView
    }

    override fun onCompletion(mp: MediaPlayer) {
        buttonRecord.setEnabled(true)
        buttonStop.setEnabled(false)
        buttonPlay.setEnabled(true)
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                    data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                        results[0]
                    }
            // Do something with spokenText
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}