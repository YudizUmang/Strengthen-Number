package com.example.strengthennumber.view.signup.fragments.grid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.strengthennumber.R

class GridAdapter(private val context : Context, private val items : List<GridItem>) : BaseAdapter() {
    private class ViewHolder {
        lateinit var imageView: ImageView
        lateinit var textView: TextView
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val gridItem = getItem(p0) as GridItem

        val view: View
        val viewHolder: ViewHolder

        if (p1 == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item, p2, false)
            viewHolder = ViewHolder()
            viewHolder.imageView = view.findViewById(R.id.choice_img)
            viewHolder.textView = view.findViewById(R.id.choice_text)
            view.tag = viewHolder
        } else {
            view = p1
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.imageView.setImageResource(gridItem.imageResId)
        viewHolder.textView.text = gridItem.text

        if (gridItem.isSelected) {
            viewHolder.imageView.setImageResource(gridItem.selectedImage)
            view.background = AppCompatResources.getDrawable(context, R.drawable.grid_selected)
            viewHolder.textView.setTextColor(context.getColor(R.color.white))

        } else {
            viewHolder.imageView.setImageResource(gridItem.imageResId)
            viewHolder.textView.text = gridItem.text
            viewHolder.textView.setTextColor(context.getColor(R.color.textColor))
            view.background = AppCompatResources.getDrawable(context,R.drawable.rectangle)
        }

        return view
    }
}