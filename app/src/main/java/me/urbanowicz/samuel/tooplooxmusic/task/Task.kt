package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable

interface Task {

    interface DoubleParam<P1, P2, R> {
        fun execute(param1: P1, param2: P2): Flowable<R>
    }

    interface SingleParam<P, R> {
        fun execute(param: P): Flowable<R>
    }

}