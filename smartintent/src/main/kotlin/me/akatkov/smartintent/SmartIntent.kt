package me.akatkov.smartintent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log

object SmartIntent {

    private val TAG = "SmartIntent"
    private val PROFILE = false
    private val blockKey = "smartIntentBlock"

    @JvmStatic
    fun <T : Activity> unwrapIntent(activity: T) {
        val startTime = System.nanoTime()
        val intent = activity.intent ?: return
        val extras = intent.extras ?: return
        val block = extras[blockKey] as? BlockSerializable<T> ?: return
        block.unwrap(activity)

        if (PROFILE) {
            Log.d(TAG, "unwrapIntent: Unloading bundle took ${System.nanoTime() - startTime} ns")
        }
    }

    @JvmStatic
    fun <T : Activity> startActivity(context: Context, destination: Class<T>, vararg properties: PropertyPair<*>, block: T.() -> Unit = {}) {
        val intent = Intent(context, destination)
        intent.putExtra(blockKey, BlockSerializable(properties, block))
        context.startActivity(intent)
    }
}