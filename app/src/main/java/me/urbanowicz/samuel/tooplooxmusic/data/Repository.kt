package me.urbanowicz.samuel.tooplooxmusic.data

interface Repository<T> {
    fun get(): Collection<T>
}