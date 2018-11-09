package com.example.andrey_radzkov

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.andrey_radzkov.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_connectrequest_detail.*
import kotlinx.android.synthetic.main.connect_request_detail.view.*

/**
 * A fragment representing a single ConnectRequest detail screen.
 * This fragment is either contained in a [ConnectRequestListActivity]
 * in two-pane mode (on tablets) or a [ConnectRequestDetailActivity]
 * on handsets.
 */
class ConnectRequestDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: DummyContent.DummyItem? = null
    private var description: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                activity?.toolbar_layout?.title = item?.content
            }
            if (it.containsKey("description")) {
                description = it.getString("description")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.connect_request_detail, container, false)


        description?.let {
            Log.d("ConnectRequestDetail: ", description)
            val nameTxt: TextView = rootView.findViewById(R.id.connect_request_detail)
            nameTxt.text = description
        }


        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
