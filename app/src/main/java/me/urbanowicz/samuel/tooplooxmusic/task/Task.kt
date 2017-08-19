package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable

interface Task<P, R> {
    fun execute(searchQuery: P): Flowable<R>
}