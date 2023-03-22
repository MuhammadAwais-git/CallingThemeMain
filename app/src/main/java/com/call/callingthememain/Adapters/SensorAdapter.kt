package com.call.callingthememain.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.R
import com.call.callingthememain.models.SensorModel

class SensorAdapter(private val context: Context, private val sensorList: ArrayList<SensorModel>,
                    private var mlistener: OnItemClickListener) :
    RecyclerView.Adapter<SensorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_sensor, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = sensorList[position]

         holder.sName.text=model.name
         holder.sresolution.text=model.resulotion
         holder.sVcode.text=model.version

        holder.sensorConstraint.setOnClickListener(View.OnClickListener {
            mlistener.onItemClick(position)
        })
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var sName: TextView
        var sresolution: TextView
        var sVcode: TextView
        var sensorConstraint: ConstraintLayout


        init {
            sensorConstraint = view.findViewById<View>(R.id.sensorConst) as ConstraintLayout
            sName = view.findViewById<View>(R.id.txtName) as TextView
            sresolution = view.findViewById<View>(R.id.txtresolution) as TextView
            sVcode = view.findViewById<View>(R.id.txtversioncode) as TextView
        }
    }

    interface OnItemClickListener {
        fun onItemClick(pos: Int)
    }
}