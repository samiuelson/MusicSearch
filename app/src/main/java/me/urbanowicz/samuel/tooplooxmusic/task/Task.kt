package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable

interface Task {

    interface SingleParam <P, R> {
        fun execute(param: P): Flowable<R>
    }

    interface NoParam<R> {
        fun execute(): Flowable<R>
    }
}