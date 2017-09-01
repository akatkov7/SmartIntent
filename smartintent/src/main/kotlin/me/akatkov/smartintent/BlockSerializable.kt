package me.akatkov.smartintent

import java.io.Serializable
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaSetter

typealias PropertyPair<R> = Pair<KMutableProperty1<*, R>, R>

/**
 * Created by akatkov on 9/1/17.
 */
internal class BlockSerializable<in T>(private val properties: Array<out PropertyPair<*>>, private val block: T.() -> Unit): Serializable {
    fun unwrap(activity: T) {
        for ((key, value) in properties) {
            key.javaSetter?.apply {
                isAccessible = true
                invoke(activity, value)
            }
        }
        activity.block()
    }
}