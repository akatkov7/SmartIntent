package me.akatkov.smartintent

import android.app.Activity
import android.os.Bundle
import android.util.Log

/**
 * Created by akatkov on 7/13/16.
 */
object SmartBundle {

    private val TAG = "SmartBundle"
    private val PROFILE = false
    private val blockKey = "smartBundleBlock"
    private val propertyKey = "smartBundleProperty"

    @JvmStatic
    fun <T : Activity> unwrapBundle(activity: T, bundle: Bundle) {
        val startTime = System.nanoTime()
        val block = bundle.get(blockKey) as? BlockSerializable<T>
        block?.unwrap(activity)
        val propertySetter = bundle.get(propertyKey) as? PropertySetterSerializable<T>
        propertySetter?.unwrap(activity)

        if (PROFILE) {
            Log.d(TAG, "unwrapBundle: Unloading bundle took ${System.nanoTime() - startTime} ns")
        }
    }

    @JvmStatic
    @SmartIntentExperimental
    fun <T : Activity> saveInstanceState(bundle: Bundle, vararg properties: PropertyPair<T, *>, block: T.() -> Unit = {}) {
        bundle.putSerializable(blockKey, BlockSerializable(properties, block))
    }

    @JvmStatic
    fun <T : Activity> saveInstanceState(bundle: Bundle, vararg properties: PropertyPair<T, *>) {
        bundle.putSerializable(blockKey, PropertySetterSerializable(properties))
    }

}
