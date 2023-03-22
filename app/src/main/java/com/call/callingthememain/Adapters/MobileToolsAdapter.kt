package com.call.callingthememain.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.call.callingthememain.R
import com.call.callingthememain.models.MobileToolsModel

class MobileToolsAdapter(private val context: Context, private val mList: ArrayList<MobileToolsModel>,
                         private var mListener: OnItemClickListener) :
    RecyclerView.Adapter<MobileToolsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_mobile_tools, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = mList[position]

            Glide.with(context)
                .load(model.Img)
                .into(holder.imgMobileTools)
        holder.txtMobileTools.text = model.name
        holder.mainCard.setCardBackgroundColor(context.resources.getColor(model.bgColor!!))

        holder.mainCard.setOnClickListener(View.OnClickListener {
            mListener.onItemClick(position)
        })
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgMobileTools: ImageView
        var txtMobileTools: TextView
        var mainCard: CardView


        init {
            imgMobileTools = view.findViewById<View>(R.id.imgMobileTools) as ImageView
            txtMobileTools = view.findViewById<View>(R.id.txtMobileTools) as TextView
            mainCard = view.findViewById<View>(R.id.cardMobileTools) as CardView
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}