package com.call.callingthememain.Adapters



import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.call.callingthememain.Hitmvvm.Model.Hit
import com.call.callingthememain.R


class PixabayAdapter(
    private val context: Context, private val picList: ArrayList<Hit>,
    private var mlistener: OnItemClickListener
) :
    RecyclerView.Adapter<PixabayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recycler_imageview, viewGroup, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = picList[position]

        holder.pb.visibility=View.VISIBLE
            Glide.with(context).load(model.largeImageURL)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.pb.visibility=View.GONE

                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.pb.visibility=View.GONE

                        return false
                    }
                })
                .into(holder.backgroundimg)

        holder.backgroundimg.setOnClickListener {
            mlistener.onItemClick(model)
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var backgroundimg: ImageView
        var pb: ProgressBar

        init {
            backgroundimg = view.findViewById<View>(R.id.imgbackground) as ImageView
            pb = view.findViewById<View>(R.id.itemProgressbar) as ProgressBar
        }
    }

    interface OnItemClickListener {
        fun onItemClick(model: Hit)
    }
}