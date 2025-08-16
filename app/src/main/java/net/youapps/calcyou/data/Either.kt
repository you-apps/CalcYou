package net.youapps.calcyou.data

sealed class Either<out L, out R> {
    class Left<A>(val value: A): Either<A, Nothing>()
    class Right<B>(val value: B): Either<Nothing, B>()
}