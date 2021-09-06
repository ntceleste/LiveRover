package com.example.liverover

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.liverover.model.Photo

/**
 * a viewadapter to handle adding items to a recyclerview
 */
internal class RoverPhotoRecyclerViewAdapter(private var itemsList: List<Photo>) :
    RecyclerView.Adapter<RoverPhotoRecyclerViewAdapter.MyViewHolder>() {

    /**
     * a viewholder used to populate the recyclerview
     */
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var itemTextView: TextView = view.findViewById(R.id.tvRoverPhotoId)

        init {
            view.setOnClickListener(this)
        }

        //handles when text view is clicked, sends photo data to detail activity view
        override fun onClick(view: View?) {
            val context = itemView.context
            val detailActivityIntent = Intent(context, DetailActivity::class.java)
            detailActivityIntent.putExtra("photo_data", itemsList[absoluteAdapterPosition])
            context.startActivity(detailActivityIntent)
        }
    }

    /**
     * creates a viewholder for the recyclerview
     * @param parent the group the viewholder should be a part of
     * @param viewType the type of view requested
     * @return the viewholder to be added to a recyclerview
     */
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_row, parent, false)
        return MyViewHolder(itemView)
    }

    /**
     * binds text to the view holder
     * @param holder the view holder to bind to
     * @param position the position of the item in the recyclerview
     */
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList[position]
        val displayText = item.id.toString() + " - " + item.camera.full_name
        holder.itemTextView.text = displayText
    }

    /**
     * gets the numbers of items in recyclerview
     * @return the number of items in the recyclerview
     */
    override fun getItemCount(): Int {
        return itemsList.size
    }
}