package com.example.andrey_radzkov.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.andrey_radzkov.R


class CustomAdapter(internal var c: Context, internal var tvShows: List<Article>) : BaseAdapter() {
    internal var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return tvShows.size
    }

    override fun getItem(position: Int): Any {
        return tvShows[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (inflater == null) {
            inflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        if (convertView == null) {
            convertView = inflater!!.inflate(R.layout.model, parent, false)

        }

        //BIND DATA
        val holder = MyHolder(convertView!!)
        holder.nameTxt.setText(tvShows[position].title)
        downloadImage(c, tvShows[position].image, holder.img)

        return convertView
    }
}