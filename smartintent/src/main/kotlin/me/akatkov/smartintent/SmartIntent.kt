package me.akatkov.smartintent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log

@RequiresOptIn(message = "This API is experimental. It may be changed in the future without notice.")
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class SmartIntentExperimental // Opt-in requirement annotation

object SmartIntent {

    private val TAG = "SmartIntent"
    private val PROFILE = false
    private val blockKey = "smartIntentBlock"
    private val propertyKey = "smartIntentProperty"

    @JvmStatic
    fun <T : Activity> unwrapIntent(activity: T) {
        val startTime = System.nanoTime()
        val intent = activity.intent ?: return
        val extras = intent.extras ?: return
        val block = extras[blockKey] as? BlockSerializable<T>
        block?.unwrap(activity)
        val propertySetter = extras[propertyKey] as? PropertySetterSerializable<T>
        propertySetter?.unwrap(activity)

        if (PROFILE) {
            Log.d(TAG, "unwrapIntent: Unloading bundle took ${System.nanoTime() - startTime} ns")
        }
    }

    @JvmStatic
    @SmartIntentExperimental
    fun <T : Activity> startActivity(context: Context, destination: Class<T>, vararg properties: PropertyPair<T, *>, block: T.() -> Unit = {}) {
        val intent = Intent(context, destination)
        intent.putExtra(blockKey, BlockSerializable(properties, block))
        context.startActivity(intent)
    }

    @JvmStatic
    fun <T : Activity> startActivity(context: Context, destination: Class<T>, vararg properties: PropertyPair<T, *>) {
        val intent = Intent(context, destination)
        intent.putExtra(propertyKey, PropertySetterSerializable(properties))
        context.startActivity(intent)
    }
}