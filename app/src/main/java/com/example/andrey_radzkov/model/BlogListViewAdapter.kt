package com.example.andrey_radzkov.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.andrey_radzkov.R


class BlogListViewAdapter(internal var c: Context, internal var articles: List<Article>) : BaseAdapter() {
    internal var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return articles.size
    }

    override fun getItem(position: Int): Any {
        return articles[position]
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
            convertView = inflater!!.inflate(R.layout.article, parent, false)

        }

        //BIND DATA
        val holder = ArticleView(convertView!!)
        holder.nameTxt.text = articles[position].title
        holder.shortDescription.text = articles[position].shortText
        downloadImage(c, articles[position].image, holder.img)

        convertView.setOnClickListener(View.OnClickListener {
            val browse = Intent(Intent.ACTION_VIEW, Uri.parse(articles[position].url))
            startActivity(c, browse, null)
        })

        return convertView
    }
}