package com.example.liverover

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.model.Photo

internal class RoverPhotoRecyclerViewAdapter(private var itemsList: List<Photo>) :
    RecyclerView.Adapter<RoverPhotoRecyclerViewAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var itemTextView: TextView = view.findViewById(R.id.tvRoverPhotoId)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val context = itemView.context
            val detailActivityIntent = Intent(context, DetailActivity::class.java)
            detailActivityIntent.putExtra("photo_data", itemsList[absoluteAdapterPosition])
            context.startActivity(detailActivityIntent)
            Log.d("RecyclerView", "CLICK!")
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        holder.itemTextView.text = item.id.toString()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}