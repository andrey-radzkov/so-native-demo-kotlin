package com.example.andrey_radzkov

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.net.URL


/**
 * @author Radzkov Andrey
 */
class BlogFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_blog, container, false)
        val thread = Thread(Runnable {
            try {
                val image: ImageView = inflate.findViewById(R.id.imageBlog)
                Picasso.with(context).load("https://www.supplyon.com/wp-content/uploads/2018/05/ILA-Berlin-2018_A380.jpg").into(image);
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start()


        return inflate
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Supplyon"
    }
}