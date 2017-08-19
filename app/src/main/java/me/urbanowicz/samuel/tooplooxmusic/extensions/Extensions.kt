package me.urbanowicz.samuel.tooplooxmusic.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.commons.lang3.StringUtils

/**
 * returns Levenshtein distance the core of Fuzzy Wuzzy search algorithm
 * https://en.wikipedia.org/wiki/Approximate_string_matching
 */
fun String.getLevenshteinDistanceTo(other: String): Int {
    return StringUtils.getLevenshteinDistance(this, other);
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
