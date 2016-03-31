package me.akatkov.smartintent

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import java.util.*
import kotlin.reflect.*

data class PropertyPair<T, V>(val prop: KMutableProperty1<T, V>, val value: V)

fun <A : KMutableProperty1<C, B>, B, C> A.assign(that: B): PropertyPair<C, B> {
    return PropertyPair(this, that)
}

class SmartIntent<T>(val context: Context, destination: Class<T>): Intent(context, destination) {
    companion object {
        @JvmStatic
        fun <T : Activity> unwrapIntent(activity: T) {
            val javaClazz = activity.javaClass
            val intent = activity.intent ?: return
            for (key in intent.extras.keySet()) {
                val value = intent.extras.get(key)
                for (field in javaClazz.declaredFields) {
                    if (key.equals(field.name)) {
                        field.isAccessible = true
                        field.set(activity, value)
                        break
                    }
                }
            }
        }
    }

    fun startActivity(vararg pairs: PropertyPair<T, *>) {
        for ((property, value) in pairs) {
            if (value != null) {
                if (value as? Boolean != null) {
                    putExtra(property.name, value as Boolean)
                } else if (value as? Array<Boolean> != null) {
                    putExtra(property.name, value as Array<Boolean>)
                } else if (value as? Byte != null) {
                    putExtra(property.name, value as Byte)
                } else if (value as? Array<Byte> != null) {
                    putExtra(property.name, value as Array<Byte>)
                } else if (value as? Char != null) {
                    putExtra(property.name, value as Char)
                } else if (value as? Array<Char> != null) {
                    putExtra(property.name, value as Array<Char>)
                } else if (value as? Short != null) {
                    putExtra(property.name, value as Short)
                } else if (value as? Array<Short> != null) {
                    putExtra(property.name, value as Array<Short>)
                } else if (value as? Int != null) {
                    putExtra(property.name, value as Int)
                } else if (value as? Array<Int> != null) {
                    putExtra(property.name, value as Array<Int>)
                } else if (value as? Long != null) {
                    putExtra(property.name, value as Long)
                } else if (value as? Array<Long> != null) {
                    putExtra(property.name, value as Array<Long>)
                } else if (value as? Float != null) {
                    putExtra(property.name, value as Float)
                } else if (value as? Array<Float> != null) {
                    putExtra(property.name, value as Array<Float>)
                } else if (value as? Double != null) {
                    putExtra(property.name, value as Double)
                } else if (value as? Array<Double> != null) {
                    putExtra(property.name, value as Array<Double>)
                } else if (value as? String != null) {
                    putExtra(property.name, value as String)
                } else if (value as? Array<String> != null) {
                    putExtra(property.name, value as Array<String>)
                } else if (value as? CharSequence != null) {
                    putExtra(property.name, value as CharSequence)
                } else if (value as? Array<CharSequence> != null) {
                    putExtra(property.name, value as Array<CharSequence>)
                } else if (value as? Parcelable != null) {
                    putExtra(property.name, value as Parcelable)
                } else if (value as? Array<Parcelable> != null) {
                    putExtra(property.name, value as Array<Parcelable>)
                } else if (value as? ArrayList<Parcelable> != null) {
                    putParcelableArrayListExtra(property.name, value as ArrayList<Parcelable>)
                } else if (value as? ArrayList<Int> != null) {
                    putIntegerArrayListExtra(property.name, value as ArrayList<Int>)
                } else if (value as? ArrayList<String> != null) {
                    putStringArrayListExtra(property.name, value as ArrayList<String>)
                } else if (value as? ArrayList<CharSequence> != null) {
                    putCharSequenceArrayListExtra(property.name, value as ArrayList<CharSequence>)
                } else if (value as? Serializable != null) {
                    putExtra(property.name, value as Serializable)
                } else if (value as? Bundle != null) {
                    putExtra(property.name, value as Bundle)
                } else {
                    throw IllegalArgumentException("Can't pass this value through an intent")
                }
            }
        }
        context.startActivity(this)
    }

}