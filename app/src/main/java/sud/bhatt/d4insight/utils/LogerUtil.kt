package sud.bhatt.d4insight.utils

import android.util.Log

private const val isDebug = true
const val UNI_TAG = "sudarshandebug"
fun exceptionLogger(TAG: String?, message: String?, exception: Exception?) {
    Log.e(TAG, message, exception)
}

fun debugLogger(TAG: String?, message: String?) {
    if (isDebug) {
        Log.d(TAG, message!!)
    }
}