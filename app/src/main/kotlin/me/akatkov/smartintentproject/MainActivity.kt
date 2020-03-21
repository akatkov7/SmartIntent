package me.akatkov.smartintentproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import me.akatkov.smartintent.SmartIntent

/**
 * Created by akatkov on 3/30/16.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSecondActivity(view: View) {
        SmartIntent.startActivity(this, SecondActivity::class.java, SecondActivity::testString to editText.text.toString())
    }
}