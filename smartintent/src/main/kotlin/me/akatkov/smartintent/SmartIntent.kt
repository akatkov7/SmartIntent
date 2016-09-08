package me.akatkov.smartintent

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.Serializable
import android.util.Log

class SmartIntent<out T>(val context: Context, destination: Class<T>): Intent(context, destination) {

    companion object {
        private val PROFILE = false
        private val blockKey = "smartIntentBlock"

        @JvmStatic
        fun <T : Activity> unwrapIntent(activity: T) {
            val startTime = System.nanoTime()
            val intent = activity.intent ?: return
            val extras = intent.extras ?: return
            val block = extras[blockKey] as? BlockSerializable<T> ?: return
            block.unwrapIntent(activity)

            if (PROFILE) {
                Log.d("SmartIntent", "unwrapIntent: Unloading bundle took ${System.nanoTime() - startTime} ns")
            }
        }
    }

    private class BlockSerializable<in T>(val block: T.() -> Unit): Serializable {
        fun unwrapIntent(activity: T) {
            activity.block()
        }
    }

    fun startActivity(block: T.() -> Unit) {
        putExtra(blockKey, BlockSerializable(block))
        context.startActivity(this)
    }

}