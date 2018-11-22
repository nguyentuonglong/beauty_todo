package com.longnguyen.beautytodo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.event.TaskDetailClickEvent
import com.longnguyen.beautytodo.event.TaskDetailDeleteEvent
import com.longnguyen.beautytodo.model.TaskDetail
import kotlinx.android.synthetic.main.item_todo_detail.view.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundResource


class TaskDetailAdapter : RecyclerView.Adapter<TaskDetailAdapter.ViewHolder>() {

    val data = mutableListOf<TaskDetail>()
    var isEditing = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            ViewType.VIEW_DATA.ordinal -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_detail, parent, false)
                DataViewHolder(view)
            }

            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_empty, parent, false)
                EmptyViewHolder(view)
            }
        }

    }

    fun updateData(data: List<TaskDetail>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun enableEditMode() {
        isEditing = true
        notifyDataSetChanged()
    }

    fun disableEditMode() {
        isEditing = false
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

    inner class DataViewHolder(itemView: View) : TaskDetailAdapter.ViewHolder(itemView) {

        fun bind(item: TaskDetail) {
            if (item.isCompleted) {
                itemView.is_complete_cb.backgroundResource = R.mipmap.ic_checked
            } else {
                itemView.is_complete_cb.backgroundResource = R.mipmap.ic_uncheck
            }

            if (isEditing) {
                itemView.delete_img.visibility = View.VISIBLE
            } else {
                itemView.delete_img.visibility = View.GONE
            }

            itemView.task_name_tv.text = item.taskName
            itemView.task_name_tv.setOnClickListener {
                if (!item.isCompleted) {
                    EventBus.getDefault().post(TaskDetailClickEvent(item))
                }
            }

            itemView.delete_img.setOnClickListener {
                EventBus.getDefault().post(TaskDetailDeleteEvent(item))
            }
        }
    }

    open inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class EmptyViewHolder(itemView: View) : TaskDetailAdapter.ViewHolder(itemView)


    enum class ViewType {
        VIEW_DATA, VIEW_EMPTY
    }
}