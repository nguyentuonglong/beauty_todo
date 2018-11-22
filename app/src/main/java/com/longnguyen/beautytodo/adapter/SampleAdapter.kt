package com.longnguyen.beautytodo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.model.Task


class SampleAdapter : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    val data = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    fun updateData(data: List<Task>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val value = data[position]
        holder.bind(value, position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Task, position: Int) {
            itemView.setOnClickListener {

            }
        }
    }
}