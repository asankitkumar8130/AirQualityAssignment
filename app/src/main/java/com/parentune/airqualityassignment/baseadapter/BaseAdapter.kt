package com.parentune.airqualityassignment.baseadapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.parentune.airqualityassignment.baseviewholder.BaseViewHolder

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<*>>() {

    var data = mutableListOf<T>()
        set(value) {
            if (field === value) return

            val diff = DiffUtil.calculateDiff(getDiffUtilCallback(field, value))
            field = value
            diff.dispatchUpdatesTo(this)
            onDataSetChanged()
        }

    fun getItem(position: Int): T {
        return data[position]
    }

    override fun getItemCount(): Int {
        return data.size
    }

    open fun getRealPosition(position: Int): Int {
        return if (itemCount <= 1) {
            0
        } else {
            position % itemCount
        }
    }

    open fun getLoopPosition(position: Int): Int {
        val realPosition = getRealPosition(position)
        return if (realPosition == 0) {
            itemCount
        } else {
            realPosition
        }
    }

    open fun onDataSetChanged() {}

    private fun getDiffUtilCallback(oldData: List<T>, newData: List<T>): DiffUtil.Callback {
        return object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areItemsTheSame(oldData[oldItemPosition], newData[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])

            override fun getOldListSize() = oldData.size

            override fun getNewListSize() = newData.size
        }
    }

    protected open fun areItemsTheSame(old: T, new: T): Boolean {
        return old == new
    }

    protected open fun areContentsTheSame(old: T, new: T): Boolean {
        return old == new
    }

    interface clickListener<I> {
        fun onItemClick(position: Int, item: I? = null)
    }
}