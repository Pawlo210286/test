package com.test.test.feature.main.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.test.test.feature.main.api.model.Rep

class RepListAdapter : ListAdapter<Rep, RepListHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepListHolder {
        return RepListHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RepListHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}