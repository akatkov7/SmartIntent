package me.akatkov.smartintentproject

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import me.akatkov.smartintent.SmartIntent
import me.akatkov.smartintent.assign

/**
 * Created by akatkov on 3/30/16.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSecondActivity(view: View) {
        SmartIntent(this, SecondActivity::class.java).startActivity(SecondActivity::value1.assign(12), SecondActivity::value2.assign(null))
    }
}