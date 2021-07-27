package com.divyanshu.androiddraw

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val IMAGE_PATH = "image_path"
class DrawAdapter(private val context: Context, private val imageList: ArrayList<String>) : RecyclerView.Adapter<DrawAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = imageList[holder.adapterPosition]
        Glide.with(context).load(path).into(holder.drawImage)
        holder.drawImage.setOnClickListener {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(IMAGE_PATH,path)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val drawImage: ImageView = itemView.findViewById(R.id.image_draw)
    }

    fun addItem(uri: Uri){
        imageList.add(uri.toString())
        notifyItemInserted(imageList.size-1)
    }
}