package me.akatkov.smartintentproject

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.second_activity_layout.*
import me.akatkov.smartintent.*

/**
 * Created by akatkov on 3/30/16.
 */
class SecondActivity : AppCompatActivity() {

    lateinit var testString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            SmartIntent.unwrapIntent(this)

            textView.text = "Passed value: " + testString
        } else {
            SmartBundle.unwrapBundle(this, savedInstanceState)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        SmartBundle(this, outState).saveInstanceState {
            // supports properties
            save(SecondActivity::testString) { value ->
                // this is called when it is restored
                testString = value
                textView.text = "Restored value: " + testString
            }
        }
    }

    fun recreate(view: View) {
        recreate()
    }

}

