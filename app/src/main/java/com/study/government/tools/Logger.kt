package com.study.government.tools

import android.util.Log
import com.study.government.tools.LogType.*

enum class LogType { E, D, I, V, W, WTF }

private const val DEFAULT_TAG = "LOGGER"
private const val DEFAULT_LABEL = ""

fun <T: Any?> T.log(
    type: LogType = I,
    tag: String = "",
    label: String = "",
    tr: Throwable? = null,
): T {
    val mes = "$label$this"
    when (type) {
        E -> Log.e(tag, mes, tr)
        D -> Log.d(tag, mes, tr)
        I -> Log.i(tag, mes, tr)
        V -> Log.v(tag, mes, tr)
        W -> Log.w(tag, mes, tr)
        WTF -> Log.wtf(tag, mes, tr)
    }
    return this
}

fun <T> T.logE(
    tag: String = DEFAULT_TAG,
    label: String = DEFAULT_LABEL,
    throwable: Throwable? = null,
) = log(E, tag, label, throwable)

fun <T> T.logI(
    tag: String = DEFAULT_TAG,
    label: String = DEFAULT_LABEL,
    throwable: Throwable? = null,
) = log(I, tag, label, throwable)