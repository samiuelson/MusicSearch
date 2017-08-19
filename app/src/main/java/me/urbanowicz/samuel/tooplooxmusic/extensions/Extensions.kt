package me.urbanowicz.samuel.tooplooxmusic.extensions

import android.content.res.AssetManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.lang3.StringUtils
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * returns Levenshtein distance, for the core of Fuzzy Wuzzy search algorithm
 * https://en.wikipedia.org/wiki/Approximate_string_matching
 */
fun String.levenshteinDistanceTo(other: String?): Int {
    val distance: Int
    if (other != null) {
        distance = StringUtils.getLevenshteinDistance(this, other)
    } else {
        distance = Int.MAX_VALUE
    }
    return distance
}

inline fun <reified T> Gson.fromJson(json: String): T = this.fromJson<T>(json, object: TypeToken<T>() {}.type)

fun AssetManager.getAsString(fileName: String): String {
    val stream = open(fileName)
    val reader = BufferedReader(InputStreamReader(stream, "UTF-8"))
    val json = reader.readText()
    reader.close()
    stream.close()
    return json
}

fun EditText.onTextChanged(callback: (String) -> Unit) {
    addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            callback.invoke(text.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    })
}

