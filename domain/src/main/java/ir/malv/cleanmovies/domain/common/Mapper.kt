package ir.malv.cleanmovies.domain.common

interface Mapper<T, R> {
    fun mapFrom(from: T): R
}

interface Aggregator<I, J, K> {
    fun transfer(first: I, second: J): K
}