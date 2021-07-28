package jones.em.domain

import jones.em.domain.denominations.*
import jones.em.domain.events.ChangeMade
import jones.em.domain.events.DenominationsPut
import jones.em.domain.events.DenominationsTaken
import jones.em.domain.events.Event
import jones.em.domain.operations.*
import java.math.BigInteger
import java.util.*
import kotlin.NoSuchElementException

fun handleOperation(state: RegisterStatus, op: Operation) = when(op){
  is MakeChange -> tryMakeChange(state, op)
  is TakeDenominations -> tryTake(state, op)
  is PutDenominations -> putDenominations(state, op)
  else -> throw Exception("Operation is not yet supported")
}

fun tryTake(state: RegisterStatus, op: TakeDenominations): Event = op.denominations.forEach {
  d -> if (state.denominationCounts.getOrDefault(d.value, USD.oneUSD(BigInteger.ZERO)).count < d.count)
    throw InsufficientFundsException()
}.let { DenominationsTaken(op.denominations) }

fun tryMakeChange(state: RegisterStatus, op: MakeChange): Event =
        getTotalInRegister(state.denominationCounts.values)
                .let {
                  makeChange(TakeChangeOperation(state.denominationCounts, it, op.amount.value, Stack(), 20))
                }
                .let { ChangeMade(it.removed.groupBy{it.value}.toList().map { (value, removals) -> USD(removals.size.toBigInteger(), value) }) }

fun putDenominations(state: RegisterStatus, op: PutDenominations): Event = DenominationsPut(op.denominations)

tailrec fun makeChange(op: TakeChangeOperation): TakeChangeOperation =
       when {
         op.changeOwed.compareTo(BigInteger.ZERO) == 0 -> op // succeeded case
         op.moneyLeft > op.changeOwed -> {
           val result = try { denominationsWhenGivenFive
                     .filter { it <= op.nextDenomination }
                     .first { denominationName -> needs(denominationName, op.changeOwed).and(hasRemaining(denominationName, op.inRegister)) }
                     .let { removeDenomination(it, op) }
           } catch (e: NoSuchElementException) {
             if(op.nextDenomination > 1)
               replaceDenomination(op)
             else
               throw InsufficientFundsException()
           }
           makeChange(result)
         }
         else -> throw InsufficientFundsException() // failed case
       }

