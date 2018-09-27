package com.example.andrey_radzkov.model

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.andrey_radzkov.R


class ArticleView(v: View) {

    internal var nameTxt: TextView
    internal var shortDescription: TextView
    internal var img: ImageView

    init {

        nameTxt = v.findViewById(R.id.nameTxt)
        shortDescription = v.findViewById(R.id.shortDescription)
        img = v.findViewById(R.id.movieImage) as ImageView

    }
}