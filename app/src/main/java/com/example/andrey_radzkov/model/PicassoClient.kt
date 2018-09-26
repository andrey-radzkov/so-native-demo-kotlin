package com.example.andrey_radzkov.model

import android.content.Context
import android.widget.ImageView
import com.example.andrey_radzkov.R
import com.squareup.picasso.Picasso

fun downloadImage(c: Context, url: String?, img: ImageView) {
    if (url != null && url.length > 0) {
        Picasso.with(c).load(url).placeholder(R.drawable.placeholder).into(img)
    } else {
        Picasso.with(c).load(R.drawable.placeholder).into(img)
    }
}