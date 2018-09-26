package com.example.andrey_radzkov

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.andrey_radzkov.model.CustomAdapter
import com.example.andrey_radzkov.model.getArticles


/**
 * @author Radzkov Andrey
 */
class BlogFragment : Fragment() {

    var lv: ListView? = null
    var adapter: CustomAdapter? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = inflater.inflate(R.layout.fragment_blog, container, false)
        adapter = CustomAdapter(context!!, getArticles())
        lv = inflate.findViewById(R.id.lv) as ListView
        lv!!.adapter = adapter
//        fab.onClickListener(View.OnClickListener { lv!!.setAdapter(adapter) })

        return inflate
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //you can set the title for your toolbar here for different fragments different titles
        activity!!.title = "Supplyon"
    }
}