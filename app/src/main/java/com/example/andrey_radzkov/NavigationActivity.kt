package com.example.andrey_radzkov

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.andrey_radzkov.service.NotificationService
import kotlinx.android.synthetic.main.navigation_activity.drawer_layout
import kotlinx.android.synthetic.main.navigation_activity.nav_view
import kotlinx.android.synthetic.main.navigation_app_bar.fab
import kotlinx.android.synthetic.main.navigation_app_bar.toolbar


open class NavigationActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var notificationService: NotificationService = NotificationService()
    @SuppressLint("SetJavaScriptEnabled")
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
            R.id.nav_voice_control -> {
                fragment = VoiceControlFragment()
            }
            R.id.nav_manage -> {
                fragment = ServiceStateFragment()
            }
            R.id.nav_create_connect -> {
                val intent = Intent(this, ConnectRequestDetailActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_complaint -> {
                fragment = BarcodeFragment()
            }
            R.id.nav_send -> {
                notificationService.sendDelayedHotification("Supplyon Pid Registraction", "Connect with 'Seller' has been activated", applicationContext, this.javaClass)
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
