package com.example.andrey_radzkov

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.navigation_activity.drawer_layout
import kotlinx.android.synthetic.main.navigation_activity.nav_view
import kotlinx.android.synthetic.main.navigation_app_bar.fab
import kotlinx.android.synthetic.main.navigation_app_bar.toolbar


open class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_activity)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        if (null == savedInstanceState) {
            displaySelectedScreen(R.id.blog_menu_item)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        displaySelectedScreen(item.itemId)

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun displaySelectedScreen(itemId: Int) {
        var fragment: Fragment? = null

        //initializing the fragment object which is selected
        when (itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
            }
            R.id.blog_menu_item -> {
                fragment = BlogFragment()
            }
            R.id.control_points_map -> {
                fragment = ControlPointsMapFragment()
            }
            R.id.nav_camera -> {
                // Handle the camera action
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 1337)
            }
            R.id.nav_voice_control -> {
                fragment = VoiceControlFragment()
            }
            R.id.nav_manage -> {
                fragment = ServiceStateFragment()
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {
                val handler = Handler()
                handler.postDelayed({
                    val notificationBuilder = NotificationCompat.Builder(applicationContext)
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("My notification")
                            .setContentText("Hello World2!")
                            .setAutoCancel(true)
                            .setVibrate(longArrayOf(150, 100, 150, 100))
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                            .setLights(Color.RED, 3000, 3000)
                            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                    // Creates an explicit intent for an Activity in your app
                    val resultIntent = Intent(applicationContext, NavigationActivity::class.java)

                    // The stack builder object will contain an artificial back stack for the
                    // started Activity.
                    // This ensures that navigating backward from the Activity leads out of
                    // your application to the Home screen.
                    val stackBuilder = TaskStackBuilder.create(applicationContext!!)
                    // Adds the back stack for the Intent (but not the Intent itself)
                    stackBuilder.addParentStack(NavigationActivity::class.java)
                    // Adds the Intent that starts the Activity to the top of the stack
                    stackBuilder.addNextIntent(resultIntent)
                    val resultPendingIntent = stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    )
                    notificationBuilder.setContentIntent(resultPendingIntent)
                    val mNotificationManager = ContextCompat.getSystemService(applicationContext!!, NotificationManager::class.java) as NotificationManager
                    // mId allows you to update the notification later on.
                    val id = 1
                    val notification = notificationBuilder.build()

                    mNotificationManager.notify(id, notification)
                }, 3000)
            }
        }

        //replacing the fragment
        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.content_frame, fragment)
            ft.commit()
        }

        val drawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
    }
}
