package com.longnguyen.beautytodo.feature.welcome

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.longnguyen.beautytodo.R
import com.longnguyen.beautytodo.feature.login.LoginActivity
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        Timer().schedule(3000) {
            LoginActivity.start(this@MainActivity)
        }
    }
}
