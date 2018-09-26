package com.example.andrey_radzkov.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.andrey_radzkov.R


class MyHolder(v: View) {

    internal var nameTxt: TextView
    internal var img: ImageView

    init {

        nameTxt = v.findViewById(R.id.nameTxt)
        img = v.findViewById(R.id.movieImage) as ImageView

    }
}