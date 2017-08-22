package me.urbanowicz.samuel.tooplooxmusic.data

import io.reactivex.Flowable

interface Repository {

    interface Simple<T> {
        fun get(): Collection<T>
    }

    interface Queryable<T> {
        fun get(query: String): Flowable<T>
    }

}