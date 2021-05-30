package com.parentune.airqualityassignment.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.parentune.airqualityassignment.Util.Util
import com.parentune.airqualityassignment.R
import com.parentune.airqualityassignment.baseadapter.BaseAdapter
import com.parentune.airqualityassignment.baseviewholder.BaseViewHolder
import com.parentune.airqualityassignment.databinding.ItemLayoutBinding
import com.parentune.airqualityassignment.model.State
import com.skydoves.bindables.binding
import com.skydoves.whatif.whatIfNotNull

class StateAdapter(
    var clickListener : clickListener<State>
) : BaseAdapter<State>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = parent.binding<ItemLayoutBinding>(R.layout.item_layout)
        return ItemViewHolder(binding).apply {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                clickListener.onItemClick(position, getItem(position))
            }
        }
    }

   inner class ItemViewHolder(var binding: ItemLayoutBinding): BaseViewHolder<State>(binding) {
       override fun bind(item: State) {
           binding.whatIfNotNull {
               it.apply {
                   state = item
                   tvtime.text = Util.getTimeAgo(item.timeage!!)
                   executePendingBindings()
               }
           }
       }
   }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = getItem(position)
        when (holder) {
            is ItemViewHolder -> {
                if(element.aqi.substring(0, 6).toFloat() <= 50){
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.good, null)
                }else if(element.aqi.substring(0, 6).toFloat() > 50 && element.aqi.substring(0, 6).toFloat() <= 100){
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.satify, null)
                }else if(element.aqi.substring(0, 6).toFloat() > 100 && element.aqi.substring(0, 6).toFloat() <= 200){
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.moderate, null)
                }else if(element.aqi.substring(0, 6).toFloat() > 200 && element.aqi.substring(0, 6).toFloat() <= 300){
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.poor, null)
                }else if(element.aqi.substring(0, 6).toFloat() > 300 && element.aqi.substring(0, 6).toFloat() <= 400){
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.very_poor, null)
                }else{
                    holder.binding.root.background = holder.binding.root.context.resources.getDrawable(R.color.severe, null)
                }
                holder.setIsRecyclable(false)
                holder.bind(element)
            }
        }
    }
}