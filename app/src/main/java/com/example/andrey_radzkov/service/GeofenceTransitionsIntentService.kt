package com.example.andrey_radzkov.service

import android.app.IntentService
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import com.example.andrey_radzkov.NavigationActivity
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

/**
 * @author Radzkov Andrey
 */
class GeofenceTransitionsIntentService : IntentService(GeofenceTransitionsIntentService.TAG) {
    private var notificationService: NotificationService = NotificationService()

    companion object {
        val TAG = "GeofenceTransitionsIS"
    }

    init {
        Log.d(TAG, "===============> GeofenceTransitionsIntentService()")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, "===============> onHandleIntent()")
        val event = GeofencingEvent.fromIntent(intent)
        if (event.hasError()) {
            Log.e(TAG, "GeofencingEvent Error: " + event.errorCode)
            return
        }
        // Get the transition type.
        val geofenceTransition = event.geofenceTransition

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER || geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger multiple geofences.
            val triggeringGeofences = event.triggeringGeofences

            // Get the transition details as a String.
            val geofenceTransitionDetails = getGeofenceTransitionDetails(this, geofenceTransition,
                    triggeringGeofences)

            // Send notification and log the transition details.
            sendNotification(geofenceTransitionDetails)
            Log.i(TAG, geofenceTransitionDetails)
        } else {
            // Log the error.
            Log.e(TAG, "===============> Error with transition.")
        }
    }

    private fun getGeofenceTransitionDetails(context: Context, geofenceTransition: Int,
                                             triggeringGeofences: List<Geofence>): String {
        Log.d(TAG, "===============> getGeofenceTransitionDetails()")
        val geofenceTransitionString = getTransitionString(geofenceTransition)
        // Get the Ids of each geofence that was triggered.
        val triggeringGeofencesIdsList = triggeringGeofences.map { geofence -> geofence.requestId }
        return geofenceTransitionString + ": " + TextUtils.join(", ", triggeringGeofencesIdsList)
    }

    private fun sendNotification(description: String) {
        Log.d(TAG, "===============> sendNotification()")
        // Create an explicit content Intent that starts the main Activity.
        val notificationIntent = Intent(applicationContext, NavigationActivity::class.java)

        // Construct a task stack.
        val stackBuilder = TaskStackBuilder.create(this)

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(NavigationActivity::class.java)

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent)

        notificationService.sendDelayedHotification("Geofencing:", description, applicationContext)
    }

    private fun getTransitionString(transitionType: Int): String {
        Log.d(TAG, "===============> getTransitionString()")
        when (transitionType) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> return "Geofencing entered"
            Geofence.GEOFENCE_TRANSITION_EXIT -> return "Geofencing exited"
            else -> return "Geofencing unknown"
        }
    }
}