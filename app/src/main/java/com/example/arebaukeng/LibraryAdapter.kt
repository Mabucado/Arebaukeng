package com.example.arebaukeng

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView


class LibraryAdapter (private val context: Context, val dataList:List<ImageTextModel>) : BaseAdapter() {

        override fun getCount(): Int {
            return dataList.size
        }

        override fun getItem(position: Int): Any {
            return dataList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            Log.i("data list",dataList.toString())
            var view = convertView
            val viewHolder: ViewHolder

            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_library,null)
                viewHolder = ViewHolder()
                viewHolder.imageView = view.findViewById(R.id.grid_image)
                viewHolder.textView = view.findViewById(R.id.item_name)
                view.tag = viewHolder
            } else {
                viewHolder = view.tag as ViewHolder
            }

            val item = dataList[position]

            // Set image
            viewHolder.imageView.setImageURI(item.imageResId)

            // Set text
            viewHolder.textView.text = item.text

            return view!!
        }

        private class ViewHolder {
            lateinit var imageView: ImageView
            lateinit var textView: TextView

        }
    }

    class ImageTextModel(val imageResId: Uri?, val text: String)

