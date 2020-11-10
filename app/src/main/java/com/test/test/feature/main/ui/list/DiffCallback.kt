package com.test.test.feature.main.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.test.test.feature.main.api.model.Rep

class DiffCallback : DiffUtil.ItemCallback<Rep>() {

    override fun areItemsTheSame(oldItem: Rep, newItem: Rep): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Rep, newItem: Rep): Boolean {
        return oldItem == newItem
    }
}