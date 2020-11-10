package com.test.test.feature.main.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.test.R
import com.test.test.feature.main.api.model.Rep
import kotlinx.android.synthetic.main.list_item_rep.view.*

class RepListHolder private constructor(v: View) : RecyclerView.ViewHolder(v) {

    fun bind(item: Rep) {
        itemView.run {
            name.text = item.name
            date.text = item.createdAt
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepListHolder {
            return LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_rep, parent, false)
                .let {
                    RepListHolder(it)
                }
        }
    }
}