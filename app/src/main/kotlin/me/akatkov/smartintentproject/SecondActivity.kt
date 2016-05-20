package me.akatkov.smartintentproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.akatkov.smartintent.SmartIntent

/**
 * Created by akatkov on 3/30/16.
 */
class SecondActivity : AppCompatActivity() {

    var value1: Int = 0
    lateinit var value2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SmartIntent.unwrapIntent(this)

        System.out.println("value1: $value1")
        System.out.println("value2: $value2")
    }

}
