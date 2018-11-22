package com.longnguyen.beautytodo.feature.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.adapter.TaskAdapter
import com.longnguyen.beautytodo.app.App.Companion.context
import com.longnguyen.beautytodo.event.TaskClickEvent
import com.longnguyen.beautytodo.model.Task
import com.longnguyen.beautytodo.util.TodoDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_list.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.jetbrains.anko.sdk25.coroutines.onClick


class ListActivity : AppCompatActivity() {

    lateinit var taskAdapter: TaskAdapter
    private var userName = ""
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        EventBus.getDefault().register(this)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun init() {
        userName = intent.getStringExtra(KEY_USER_NAME) ?: "Unknown"
        hello_title.text = String.format(this.getString(R.string.hello_user), userName)

        add_task_img.onClick {
            if (enter_todo_edt.text.isNotEmpty()) {
                TodoDatabase.getInstance().taskDao().insertTask(
                    Task(
                        userName = userName,
                        taskName = enter_todo_edt.text.toString()
                    )
                )
                reloadTask()
                enter_todo_edt.text.clear()
            } else {
                Toast.makeText(this@ListActivity, "Please fill task name", Toast.LENGTH_SHORT).show()
            }

        }
        taskAdapter = TaskAdapter()
        todo_rv.layoutManager = LinearLayoutManager(context.applicationContext)
        todo_rv.itemAnimator = DefaultItemAnimator()
        todo_rv.adapter = taskAdapter
        reloadTask()
    }

    private fun reloadTask() {
        compositeDisposable.addAll(
            TodoDatabase.getInstance().taskDao().getTaskByUserName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    taskAdapter.updateData(it)
                }, {

                })
        )
    }

    @Subscribe
    fun onTaskClickEvent(event: TaskClickEvent) {
        ListDetailActivity.start(this, event.userName, event.taskName, event.taskId)
    }

    companion object {

        const val KEY_USER_NAME = "KEY_USER_NAME"

        fun start(context: Context, userName: String) {
            val intent = Intent(context, ListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(KEY_USER_NAME, userName)
            context.startActivity(intent)
        }

    }
}