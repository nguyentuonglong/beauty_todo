package com.longnguyen.beautytodo.feature.signup


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.feature.list.ListActivity
import com.longnguyen.beautytodo.model.User
import com.longnguyen.beautytodo.util.TodoDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init() {
        sign_up_btn.onClick {
            if (user_name_edt.text.isNotEmpty() && password_edit_text.text.isNotEmpty() && re_password_edt.text.isNotEmpty()) {
                if (password_edit_text.text.toString() != re_password_edt.text.toString()) {
                    Toast.makeText(this@SignUpActivity, "Please match your password", Toast.LENGTH_SHORT).show()
                } else {
                    insertUser(user_name_edt.text.toString(), password_edit_text.text.toString())
                }
            } else {
                Toast.makeText(this@SignUpActivity, "Please fill information", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertUser(name: String, password: String) {
        val user = User(name = name, password = password)
        TodoDatabase.getInstance().userDao().insertUser(user)
        ListActivity.start(this, name)
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }

    }
}