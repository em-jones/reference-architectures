package jones.em.domain

import java.util.*
import java.util.function.Predicate

const val argTestPattern = "\\d*(\\s\\d*)*"
val argRepetitionRegex = {v: Int -> ("$argTestPattern{${v - 1}}").toRegex()}

fun validator(argCount: Int) = { v: String -> argRepetitionRegex(argCount).matches(v) }
fun <T>validateNullable(v: T, validator: Predicate<T>): Optional<T> = Optional.ofNullable(v).filter(validator)
fun errMessage(i: Int, input: String) = "Expected $i parameters given input: '$input'"
fun getIfArgCountIsValid(argCount: Int) = fun(args: String) =
        validateNullable(args, validator(argCount)).orElseThrow { Exception(errMessage(argCount, args)) }

val whenArgCountIsFiveStrategy = getIfArgCountIsValid(5)
val whenArgCountIsOneStrategy = getIfArgCountIsValid(1)
