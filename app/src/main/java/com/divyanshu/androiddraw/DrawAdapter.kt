package com.divyanshu.androiddraw

import android.content.res.Resources
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class DrawAdapter(private val filePath: ArrayList<String>) : RecyclerView.Adapter<DrawAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filePath.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val path = filePath[holder.adapterPosition]
        holder.drawImage.setImageURI(Uri.parse(path))
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val drawImage: ImageView = itemView.findViewById(R.id.image_draw)
    }

}