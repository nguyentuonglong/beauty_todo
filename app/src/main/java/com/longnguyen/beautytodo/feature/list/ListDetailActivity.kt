package com.longnguyen.beautytodo.feature.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.adapter.TaskDetailAdapter
import com.longnguyen.beautytodo.app.App
import com.longnguyen.beautytodo.event.TaskDetailClickEvent
import com.longnguyen.beautytodo.event.TaskDetailDeleteEvent
import com.longnguyen.beautytodo.model.TaskDetail
import com.longnguyen.beautytodo.util.TodoDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.sdk25.coroutines.onClick

class ListDetailActivity : AppCompatActivity() {

    private lateinit var userName: String
    private lateinit var taskName: String
    private lateinit var taskId: String
    private lateinit var unDoneAdapter: TaskDetailAdapter
    private lateinit var doneAdapter: TaskDetailAdapter
    private val compositeDisposable = CompositeDisposable()
    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun init() {
        userName = intent.getStringExtra(KEY_USER_NAME_ON_DETAIL) ?: "Unknown"
        taskName = intent.getStringExtra(KEY_TASK_NAME_ON_DETAIL) ?: "Unknown"
        taskId = intent.getStringExtra(KEY_TASK_ID_ON_DETAIL) ?: "Unknown"
        unDoneAdapter = TaskDetailAdapter()
        doneAdapter = TaskDetailAdapter()

        todo_name_tv.text = taskName

        undone_rv.layoutManager = LinearLayoutManager(App.context.applicationContext)
        undone_rv.itemAnimator = DefaultItemAnimator()
        undone_rv.adapter = unDoneAdapter

        done_rv.layoutManager = LinearLayoutManager(App.context.applicationContext)
        done_rv.itemAnimator = DefaultItemAnimator()
        done_rv.adapter = doneAdapter

        add_detail_task_img.onClick {
            if (enter_todo_edt.text.isNotEmpty()) {
                TodoDatabase.getInstance().taskDetail()
                    .insertTaskDetail(
                        TaskDetail(
                            taskId = taskId,
                            taskName = enter_todo_edt.text.toString(),
                            isCompleted = false
                        )
                    )
                reloadDetailTask()
                enter_todo_edt.text.clear()
            }
        }

        edit_tv.onClick {
            if (!isEditing) {
                isEditing = true
                unDoneAdapter.enableEditMode()
                doneAdapter.enableEditMode()
                edit_tv.text = getString(R.string.done)
            } else {
                isEditing = false
                unDoneAdapter.disableEditMode()
                doneAdapter.disableEditMode()
                edit_tv.text = getString(R.string.edit)
            }

        }

        back_tv.onClick {
            this@ListDetailActivity.finish()
        }

        reloadDetailTask()
    }

    private fun reloadDetailTask() {
        compositeDisposable.addAll(
            TodoDatabase.getInstance().taskDetail().getTaskDetailByTaskId(taskId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNotEmpty()) {
                        unDoneAdapter.updateData(it.filter { !it.isCompleted })
                        doneAdapter.updateData(it.filter { it.isCompleted })
                    } else {
                        unDoneAdapter.updateData(it)
                        doneAdapter.updateData(it)
                    }
                }, {

                })
        )
    }

    @Subscribe
    fun onDetailClickEvent(event: TaskDetailClickEvent) {
        val data = event.taskDetail
        data.isCompleted = true
        TodoDatabase.getInstance().taskDetail().update(data)
        reloadDetailTask()
    }

    @Subscribe
    fun onDeleteTaskEvent(event: TaskDetailDeleteEvent) {
        TodoDatabase.getInstance().taskDetail().delete(event.taskDetail)
        reloadDetailTask()
    }

    companion object {
        const val KEY_USER_NAME_ON_DETAIL = "KEY_USER_NAME_ON_DETAIL"
        const val KEY_TASK_NAME_ON_DETAIL = "KEY_TASK_NAME_ON_DETAIL"
        const val KEY_TASK_ID_ON_DETAIL = "KEY_TASK_ID_ON_DETAIL"
        fun start(context: Context, userName: String, taskName: String, taskId: String) {
            val intent = Intent(context, ListDetailActivity::class.java)
            intent.putExtra(KEY_USER_NAME_ON_DETAIL, userName)
            intent.putExtra(KEY_TASK_NAME_ON_DETAIL, taskName)
            intent.putExtra(KEY_TASK_ID_ON_DETAIL, taskId)
            context.startActivity(intent)
        }

    }
}