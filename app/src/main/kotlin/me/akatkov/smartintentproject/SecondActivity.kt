package me.akatkov.smartintentproject

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.second_activity_layout.*
import me.akatkov.smartintent.SmartBundle
import me.akatkov.smartintent.SmartIntent

/**
 * Created by akatkov on 3/30/16.
 */
class SecondActivity : AppCompatActivity() {

    lateinit var testString: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity_layout)

        SmartIntent.unwrapIntent(this)
        // set up your activity now that your properties are set up
        textView.text = "Passed value: " + testString
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        SmartBundle.unwrapBundle(this, savedInstanceState)

        textView.text = "Restored value: $testString"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        SmartBundle.saveInstanceState<SecondActivity>(outState, SecondActivity::testString to testString)
    }

    fun recreate(view: View) {
        recreate()
    }

}

