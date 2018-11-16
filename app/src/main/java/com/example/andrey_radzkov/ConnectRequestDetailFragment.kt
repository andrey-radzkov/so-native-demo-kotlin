package com.example.andrey_radzkov

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.andrey_radzkov.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_nwl_request_detail.toolbar_layout

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
            if (it.containsKey("description")) {
                description = it.getString("description")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.nwl_request_detail, container, false)

        val orgLogo: ImageView = activity!!.findViewById(R.id.company_logo)
        val orgLogoCard: CardView = activity!!.findViewById(R.id.company_logo_card)
        val companyInfoText: TextView = activity!!.findViewById(R.id.company_info_text)
        description?.let {
            Log.d("ConnectRequestDetail: ", description)
            val nameTxt: TextInputLayout = rootView.findViewById(R.id.connect_request_detail_input)
            activity?.toolbar_layout?.title = description
            nameTxt.setVisibility(View.GONE)
            if (description == "Epam, Minsk") {
                orgLogo.setImageResource(R.drawable.epam_logo)
                companyInfoText.setText("Software development company")
            } else {
                companyInfoText.setText("I`m livng here")
            }
        } ?: run {
            val toolbarText: TextView? = activity?.findViewById(R.id.toolbar_text)
            toolbarText!!.setVisibility(View.INVISIBLE)
            orgLogoCard.setVisibility(View.INVISIBLE)
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
