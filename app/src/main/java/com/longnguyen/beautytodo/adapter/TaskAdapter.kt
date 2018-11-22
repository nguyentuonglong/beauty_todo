package com.longnguyen.beautytodo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.event.TaskClickEvent
import com.longnguyen.beautytodo.model.Task
import kotlinx.android.synthetic.main.item_todo.view.*
import org.greenrobot.eventbus.EventBus


class TaskAdapter : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    val data = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            ViewType.VIEW_DATA.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
                DataViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                EmptyViewHolder(view)
            }
        }

    }

    fun updateData(data: List<Task>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (data.size == 0) ViewType.VIEW_EMPTY.ordinal else ViewType.VIEW_DATA.ordinal
    }

    override fun getItemCount(): Int {
        return if (data.size == 0) 1 else data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (data.size == 0) {
            (holder as EmptyViewHolder)
        } else {
            val value = data[position]
            (holder as DataViewHolder).bind(value)
        }

    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    inner class DataViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bind(item: Task) {
            itemView.task_name_tv.text = item.taskName
            itemView.setOnClickListener {
                EventBus.getDefault().post(TaskClickEvent(item.userName!!, item.taskName!!, item.id!!))
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : ViewHolder(itemView)


    enum class ViewType {
        VIEW_DATA, VIEW_EMPTY
    }
}