package ir.malv.cleanmovies.domain.common

interface Mapper {
    fun <T, R> mapFrom(from: T): R
}