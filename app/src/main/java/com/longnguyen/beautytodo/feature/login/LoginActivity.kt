package com.longnguyen.beautytodo.feature.login


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.feature.list.ListActivity
import com.longnguyen.beautytodo.feature.signup.SignUpActivity
import com.longnguyen.beautytodo.util.TodoDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class LoginActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        login_btn.onClick {
            if (email_edit_text.text.isNotEmpty() && password_edit_text.text.isNotEmpty()) {
                login(email_edit_text.text.toString(), password_edit_text.text.toString())
            } else {
                Toast.makeText(this@LoginActivity, "Please fill information", Toast.LENGTH_SHORT).show()
            }
        }

        sign_up_btn.onClick {
            SignUpActivity.start(this@LoginActivity)
        }
    }

    private fun login(userName: String, password: String) {
        compositeDisposable.addAll(
            TodoDatabase.getInstance().userDao().getUser(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it != null) {
                        ListActivity.start(this, it!!.name!!)
                    } else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }, {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                })
        )
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }

    }
}