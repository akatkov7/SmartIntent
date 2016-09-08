package me.akatkov.smartintent

import android.app.Activity
import android.os.Bundle
import android.util.Log
import java.io.Serializable
import java.io.UnsupportedEncodingException
import kotlin.reflect.KMutableProperty1

/**
 * Created by akatkov on 7/13/16.
 */
class SmartBundle<T>(val activity: T, val outBundle: Bundle): Serializable {

    companion object {
        private val PROFILE = false
        private val blockKey = "smartBundleBlock"

        @JvmStatic
        fun <T : Activity> unwrapBundle(activity: T, bundle: Bundle) {
            val startTime = System.nanoTime()
            val block = bundle.get(blockKey) as? BlockListSerializable<T> ?: return
            block.unwrapBundle(activity, bundle)

            if (PROFILE) {
                Log.d("SmartBundle", "unwrapBundle: Unloading bundle took ${System.nanoTime() - startTime} ns")
            }
        }
    }

    private class BlockListSerializable<in T>(val blocks: Map<String, T.(value: Any) -> Unit>): Serializable {
        fun unwrapBundle(activity: T, bundle: Bundle) {
            for (key in bundle.keySet()) {
                val value = bundle.get(key) ?: continue
                blocks[key]?.invoke(activity, value)
            }
        }
    }

    private val blocks: MutableMap<String, T.(value: Any) -> Unit> = mutableMapOf()

    fun saveInstanceState(block: SmartBundle<T>.() -> Unit) {
        this.block()
        outBundle.putSerializable(blockKey, BlockListSerializable(blocks))
    }

    fun <R> save(property: KMutableProperty1<T, R>, onRestore: T.(value: R) -> Unit) {
        saveValue(property.name, property.get(activity))
        blocks[property.name] = onRestore as T.(value: Any) -> Unit
    }

    fun <R> save(key: String, value: R, onRestore: T.(value: R) -> Unit) {
        saveValue(key, value)
        blocks[key] = onRestore as T.(value: Any) -> Unit
    }

    // TODO: add rest of Bundle put* options
    private fun <R> saveValue(key: String, value: R) {
        when (value) {
            is Boolean -> outBundle.putBoolean(key, value)
            is BooleanArray -> outBundle.putBooleanArray(key, value)
            is Int -> outBundle.putInt(key, value)
            is String -> outBundle.putString(key, value)
            else -> throw UnsupportedEncodingException("Unknown value to store in Bundle!")
        }
    }
}
