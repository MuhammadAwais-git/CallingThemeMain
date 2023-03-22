package com.call.callingthememain.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.call.callingthememain.R
import com.call.callingthememain.models.CallThemeModel

class CallThemeAdapter(private val context: Context, private val mList: ArrayList<CallThemeModel>,
                       private var mListener: OnItemClickListener
) :

    RecyclerView.Adapter<CallThemeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_call_theme, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

         val model = mList[position]

            Glide.with(context)
                .load(model.Img)
                .into(holder.imgCallTheme)

        holder.imgCallTheme.setOnClickListener {
            mListener.onItemClick(model)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgCallTheme: ImageView

        init {
            imgCallTheme = view.findViewById<View>(R.id.imgCallTheme) as ImageView

        }
    }
    interface OnItemClickListener {
        fun onItemClick(model: CallThemeModel)

    }
}